package net.vionta.salvora.server.routings;

import static net.vionta.salvora.server.response.Response.close;
import static net.vionta.salvora.server.response.Response.writeContent;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.http.annotation.Obsolete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.sun.mail.iap.Response;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import net.vionta.salvora.config.dto.Parameter;
import net.vionta.salvora.config.dto.ParameterSetElement;
import net.vionta.salvora.config.dto.PathParameter;
import net.vionta.salvora.config.dto.RequestParameter;
import net.vionta.salvora.config.dto.Transformation;
import net.vionta.salvora.config.dto.TransformationStep;
import net.vionta.salvora.server.launch.Options;
import net.vionta.salvora.server.request.ParameterCalculation;
import net.vionta.salvora.server.response.error.ErrorManager;
import net.vionta.salvora.server.routings.path.DefaultPathCalculator;
import net.vionta.salvora.server.routings.path.IPathCalculator;
import net.vionta.salvora.util.file.FolderList;
import net.vionta.salvora.util.file.NetworkFileReader;
import net.vionta.salvora.util.xml.StringReplacementTransform;
import net.vionta.salvora.util.xml.XsltTransform;

/**
 * Creates the vertx mapping for the transformations, according to the mapping
 * file.
 */
public class TransformationRoutings {

	private static Logger LOGGER = LoggerFactory.getLogger(TransformationRoutings.class);
	private static List<PathParameter> pathParameters;

	static IPathCalculator pathCalculator = new DefaultPathCalculator();

	/**
	 * Configures a vertx route for a determined transformation.
	 * 
	 * @param router
	 * @param transformation the defined transformation.
	 * @param options        The options object.
	 */
	public static void routeTransformation(Router router, Transformation transformation, Options options) {
		LOGGER.info("Logging transformation " + transformation);
		String vertxRoute =  pathCalculator.calculateVertxPath(transformation); 
		LOGGER.debug("Vertx Route : " + vertxRoute);
		router.route().path(vertxRoute).method(HttpMethod.GET).handler(request -> {
			HttpServerResponse response = request.response();
			LOGGER.debug("Logging transformation " + transformation);
			try {
				TriggerChainProcces.beforeTriggers(transformation.getTriggers(), request);
				if (Transformation.LOCAL_SOURCE_TYPE.equals(transformation.getType())) {
					localSourceRoute(transformation, request, response);
				} else if (Transformation.REMOTE_NETWORK_SOURCE_TYPE.equals(transformation.getType())) {
					RemoteNetworkSource(transformation, options, request, response);
				} else if (Transformation.LOCAL_DIRECTORY_LISTING.equals(transformation.getType())) {
					LocalDirectoryMapping(transformation, options, request, response);
				}
				TriggerChainProcces.afterTriggers(transformation.getTriggers(), request);
			} catch (TransformerException | SAXException | IOException | ParserConfigurationException e) {
				LOGGER.warn("Error while performing transformation : ", e.getMessage());
				ErrorManager.notifyError(response,
						"Transformation type not recognised or not informed, valid values are local_file, directory_listing and remote_network ");
			}
			close(response);
		});
	}

	private static void localSourceRoute(Transformation transformation, RoutingContext request,
			HttpServerResponse response) {
		try {
			LOGGER.debug("Local Source: 1");
			LOGGER.debug(transformation.toString());
			LOGGER.debug(" Steps " + (transformation.getTransformationSteps() != null));
			LOGGER.debug(" Steps " + transformation.getTransformationSteps().size());
			Iterator<TransformationStep> it = transformation.getTransformationSteps().iterator();
			TransformationStep step = it.next();
			LOGGER.debug("Step 2 : " + step.getName() + " - " + step.getType());
			String content;
			Map<String, String> parameters = ParameterCalculation.buildTransformationParameterMap(step, request);
			LOGGER.debug("Step 2" + request.normalisedPath() + " " + step.getType());
			LOGGER.debug(" - " + transformation.getBasePath());
			LOGGER.debug(" - " + transformation.getBaseInternalPath());
			String convertInternalUrl = pathCalculator.calculateInternalPath(
					transformation, request.normalisedPath(), request.pathParams());
			LOGGER.debug(" Internal URL " + convertInternalUrl);
			if (TransformationStep.TRANSFORMATION_TYPE_XSLT.equals(step.getType())) {
				content = XsltTransform.transformDocument(convertInternalUrl, step.getSource(), parameters);
			} else if (TransformationStep.TRANSFORMATION_TYPE_STRING.equals(step.getType())) {
				LOGGER.debug("Transforming String");
				LOGGER.debug("Parameters :" + parameters);
				content = StringReplacementTransform.transformDocument(convertInternalUrl, parameters);
			} else
				throw new IllegalStateException("Transformation type not configured or non valid yet");
			LOGGER.debug("conent:  " + content);
			content = chainNextTransformations(it, content, request);
			LOGGER.debug("conent (chained) :  " + content);
			writeContent(response, request.request(), content);
		} catch (Exception e) {
			LOGGER.warn("Error in transformation: " + e.getCause());
			LOGGER.warn("Error in transformation: " + e.getMessage());
			e.printStackTrace();
			ErrorManager.notifyError(response, "Error handling transformation");
		}
	}

	private static void LocalDirectoryMapping(Transformation transformation, Options options, RoutingContext request,
			HttpServerResponse response) throws MalformedURLException, IOException, TransformerException, SAXException,
			ParserConfigurationException {
		String internalPath = pathCalculator.calculateInternalPath(transformation, request.normalisedPath() , request.pathParams());
		LOGGER.debug("Call URL  (1) " + internalPath);
		String initialContent = FolderList.printFolderContent(internalPath);
		String content = chainTransformations(transformation.getTransformationSteps(), initialContent, request);
		LOGGER.debug("Content : " + content);
		writeContent(response, request.request(), content);
	}

	private static void RemoteNetworkSource(Transformation transformation, Options options, RoutingContext request,
			HttpServerResponse response) throws MalformedURLException, IOException, TransformerException, SAXException,
			ParserConfigurationException {
		LOGGER.info("Transforming " + transformation.getName() + " | " + transformation.getPath() + " -> "
				+ transformation.getInternalPath());
		String calculateCallUrl  = pathCalculator.calculateInternalNetworkPath(
				transformation, request.normalisedPath(), 
				String.valueOf(options.getPort()), TransformationUrlCalculation.HTTP_SCHEME, 
				request.pathParams());
		LOGGER.debug("Call URL  (1) " + calculateCallUrl);
		URL initialContentUrl = new URL(calculateCallUrl);
		LOGGER.debug("Initial URL  (1) " + initialContentUrl);

		String initialContent = NetworkFileReader.readNetworkUrlToString(initialContentUrl);
		LOGGER.debug("Content : " + initialContent);
		String content = chainTransformations(transformation.getTransformationSteps(), initialContent, request);
		LOGGER.debug("Content : " + content);
		writeContent(response, request.request(), content);
	}

	/**
	 * Prepares the list of parameters from the step.
	 * 
	 * @param step
	 * @param request
	 * @return The list of parameters for the Xslt Transformation
	 */
	@Deprecated
	private static Map<String, String> buildTransformationParameterMap(ParameterSetElement step,
			RoutingContext request) {
		Map<String, String> transformationParameters = new Hashtable<String, String>();
		// Handle list of path Parameters
		for (PathParameter param : step.getPathParameters()) {

			String pathParamKey = param.getRequestKey();
			LOGGER.debug("Getting Path Param :" + pathParamKey + " for " + param.getTransformationParamName());
			String pathParam = request.pathParam(pathParamKey);
			LOGGER.debug("Path Param :" + pathParamKey + " -> " + pathParam);
			transformationParameters.put(param.getTransformationParamName(), pathParam);
			LOGGER.debug("[" + param.getTransformationParamName() + " -> " + pathParam + "]");
		}

		// Handle list of query params
		for (RequestParameter param : step.getRequestParameters()) {
			// Query params have multiple values
			List<String> queryParam = request.queryParam(param.getRequestKey());
			if (queryParam != null && !queryParam.isEmpty()) {
				String queryParamValue = queryParam.get(0);
				transformationParameters.put(param.getTransformationParamName(), queryParamValue);
			}
		}
		// Handle list of simple params
		LOGGER.debug(" Transformation Parameters {} ",transformationParameters);
		for (Parameter param : step.getParameters()) {
			LOGGER.debug(" Params {} -> {} ",param.getName() , param.getValue());
			transformationParameters.put(param.getName(), param.getValue());
		}
		return transformationParameters;
	}

	/**
	 * Performs a chain of transformations over a content string.
	 * 
	 * @param transformationSteps
	 * @param initialContent
	 * @return
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 * @throws SAXException
	 * @throws IOException
	 */
	private static String chainTransformations(List<TransformationStep> transformationSteps, String initialContent,
			RoutingContext request)
			throws TransformerException, SAXException, IOException, ParserConfigurationException {
		String content = initialContent;
		LOGGER.info("Initial Content: " + content);
		for (TransformationStep step : transformationSteps) {
			LOGGER.info("Step : " + step.getName());
			Map<String, String> parameters = buildTransformationParameterMap(step, request);
			content = XsltTransform.transformDocumentFromContent(content, step.getSource(), parameters);
			LOGGER.debug(" Content : " + content);
		}
		return content;
	}

	/**
	 * Executes a local transformation on the first element and afterwards it chains
	 * the transformations using the generated content.
	 * 
	 * TODO : Add parameter binding from url routes, etc.
	 * 
	 * @param it
	 * @param content
	 * @return
	 * @throws TransformerException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	private static String chainNextTransformations(Iterator<TransformationStep> it, String content,
			RoutingContext request)
			throws TransformerException, SAXException, IOException, ParserConfigurationException {
		TransformationStep step;
		if (it.hasNext())
			do {
				step = it.next();
				Map<String, String> parameters = ParameterCalculation.buildTransformationParameterMap(step, request);
				if (step.TRANSFORMATION_TYPE_XSLT.equals(step.getType())) {
					content = XsltTransform.transformDocumentFromContent(content, step.getSource(), parameters);
				} else if (TransformationStep.TRANSFORMATION_TYPE_STRING.equals(step.getType())) {
					content = StringReplacementTransform.transformDocumentFromContent(content, parameters);
				} else
					throw new IllegalStateException("Transformation type not configured or non valid yet");
				LOGGER.info("Content n :" + net.vionta.salvora.server.response.Response.contentHint(content));
			} while (it.hasNext());
		return content;
	}

}