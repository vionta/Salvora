package net.vionta.salvora.server.routing;


import static net.vionta.salvora.server.response.Response.writeContent;
import static net.vionta.salvora.server.routing.TransformationStepListRoute.chainNextTransformations;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import net.vionta.salvora.config.dto.FileMapping;
import net.vionta.salvora.config.dto.TransformationStep;
import net.vionta.salvora.server.request.ParameterCalculation;
import net.vionta.salvora.server.response.error.ErrorManager;
import net.vionta.salvora.server.routings.path.DefaultPathCalculator;
import net.vionta.salvora.server.routings.path.IPathCalculator;
import net.vionta.salvora.util.xml.StringReplacementTransform;
import net.vionta.salvora.util.xml.XsltTransform;

public class DefaultTransformationManager {
	
	private static Logger LOGGER = LoggerFactory.getLogger(DefaultTransformationManager.class);
	static IPathCalculator pathCalculator = new DefaultPathCalculator();

//	public static void executeTransformationChain(Transformation transformation, Options options, RoutingContext request) {
//			HttpServerResponse response = request.response();
//			LOGGER.debug("Logging transformation " + transformation);
//			localSourceRoute(transformation, request, response);
//			close(response);
//		}
	
//	public static void transformationRouteAction(Transformation transformation, Options options,
//			RoutingContext request) {
//		HttpServerResponse response = request.response();
//		LOGGER.debug("Logging transformation " + transformation);
//		try {
//			TriggerChainProcces.beforeTriggers(transformation.getTriggers(), request);
//			if (Transformation.LOCAL_SOURCE_TYPE.equals(transformation.getType())) {
//				localSourceRoute(transformation, request, response);
//			} else if (Transformation.REMOTE_NETWORK_SOURCE_TYPE.equals(transformation.getType())) {
//				RemoteNetworkSource(transformation, options, request, response);
//			} else if (Transformation.LOCAL_DIRECTORY_LISTING.equals(transformation.getType())) {
//				LocalDirectoryMapping(transformation, options, request, response);
//			} else if (Transformation.LOCAL_NETWORK_SOURCE_TYPE.equals(transformation.getType())) 
//				LocalNetworkSource(transformation, options, request, response);
//			TriggerChainProcces.afterTriggers(transformation.getTriggers(), request);
//		} catch (TransformerException | SAXException | IOException | ParserConfigurationException e) {
//			LOGGER.warn("Error while performing transformation : ", e.getMessage());
//			ErrorManager.notifyError(response,
//					"Transformation type not recognised or not informed, valid values are local_file, directory_listing and remote_network ");
//		}
//		close(response);
//	}

	
	public static void executeTransformationSteps(FileMapping fileMapping, List<TransformationStep> transformationStepList, RoutingContext request,
			HttpServerResponse response) {
		try {
			Iterator<TransformationStep> it = transformationStepList.iterator();
			TransformationStep step = it.next();
			LOGGER.debug("Step 2 : " + step.getName() + " - " + step.getType());
			String content;
			Map<String, String> parameters = ParameterCalculation.buildTransformationParameterMap(step, request);
			LOGGER.debug("Step 2" + request.normalisedPath() + " " + step.getType());
			LOGGER.debug(" - " + fileMapping.getBasePath());
			LOGGER.debug(" - " + fileMapping.getBaseInternalPath());
			String convertInternalUrl = pathCalculator.calculateInternalPath(
					fileMapping, request.normalisedPath(), request.pathParams());
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
	
	
//	private static void localSourceRoute(Transformation transformation, RoutingContext request,
//			HttpServerResponse response) {
//		try {
//			LOGGER.debug("Local Source: 1");
//			LOGGER.debug(transformation.toString());
//			LOGGER.debug(" Steps " + (transformation.getTransformationSteps() != null));
//			LOGGER.debug(" Steps " + transformation.getTransformationSteps().size());
//			Iterator<TransformationStep> it = transformation.getTransformationSteps().iterator();
//			TransformationStep step = it.next();
//			LOGGER.debug("Step 2 : " + step.getName() + " - " + step.getType());
//			String content;
//			Map<String, String> parameters = ParameterCalculation.buildTransformationParameterMap(step, request);
//			LOGGER.debug("Step 2" + request.normalisedPath() + " " + step.getType());
//			LOGGER.debug(" - " + transformation.getBasePath());
//			LOGGER.debug(" - " + transformation.getBaseInternalPath());
//			String convertInternalUrl = pathCalculator.calculateInternalPath(
//					transformation, request.normalisedPath(), request.pathParams());
//			LOGGER.debug(" Internal URL " + convertInternalUrl);
//			if (TransformationStep.TRANSFORMATION_TYPE_XSLT.equals(step.getType())) {
//				content = XsltTransform.transformDocument(convertInternalUrl, step.getSource(), parameters);
//			} else if (TransformationStep.TRANSFORMATION_TYPE_STRING.equals(step.getType())) {
//				LOGGER.debug("Transforming String");
//				LOGGER.debug("Parameters :" + parameters);
//				content = StringReplacementTransform.transformDocument(convertInternalUrl, parameters);
//			} else
//				throw new IllegalStateException("Transformation type not configured or non valid yet");
//			LOGGER.debug("conent:  " + content);
//			content = chainNextTransformations(it, content, request);
//			LOGGER.debug("conent (chained) :  " + content);
//			writeContent(response, request.request(), content);
//		} catch (Exception e) {
//			LOGGER.warn("Error in transformation: " + e.getCause());
//			LOGGER.warn("Error in transformation: " + e.getMessage());
//			e.printStackTrace();
//			ErrorManager.notifyError(response, "Error handling transformation");
//		}
//	}
//
//	private static void LocalDirectoryMapping(Transformation transformation, Options options, RoutingContext request,
//			HttpServerResponse response) throws MalformedURLException, IOException, TransformerException, SAXException,
//			ParserConfigurationException {
//		String internalPath = pathCalculator.calculateInternalPath(transformation, request.normalisedPath() , request.pathParams());
//		LOGGER.debug("Call URL  (1) " + internalPath);
//		String initialContent = FolderList.printFolderContent(internalPath);
//		String content = chainTransformations(transformation.getTransformationSteps(), initialContent, request);
//		LOGGER.debug("Content : " + content);
//		writeContent(response, request.request(), content);
//	}
//
//	private static void RemoteNetworkSource(Transformation transformation, Options options, RoutingContext request,
//			HttpServerResponse response) throws MalformedURLException, IOException, TransformerException, SAXException,
//			ParserConfigurationException {
//		LOGGER.info("Transforming " + transformation.getName() + " | " + transformation.getPath() + " -> "
//				+ transformation.getInternalPath());
//		String calculateCallUrl  = pathCalculator.calculateInternalNetworkPath(
//				transformation, request.normalisedPath(), 
//				String.valueOf(options.getPort()), Vers1TransformationManager.HTTP_SCHEME, 
//				request.pathParams());
//		LOGGER.debug("Call URL  (1) " + calculateCallUrl);
//		URL initialContentUrl = new URL(calculateCallUrl);
//		LOGGER.debug("Initial URL  (1) " + initialContentUrl);
//
//		String initialContent = NetworkFileReader.readNetworkUrlToString(initialContentUrl);
//		LOGGER.debug("Content : " + initialContent);
//		String content = chainTransformations(transformation.getTransformationSteps(), initialContent, request);
//		LOGGER.debug("Content : " + content);
//		writeContent(response, request.request(), content);
//	}
//
//	private static void LocalNetworkSource(Transformation transformation, Options options, RoutingContext request,
//			HttpServerResponse response) throws MalformedURLException, IOException, TransformerException, SAXException,
//	ParserConfigurationException {
//		
//		LOGGER.info("Transforming " + transformation.getName() + " | " + transformation.getPath() + " -> "
//				+ transformation.getInternalPath());
//		String calculateCallUrl  = pathCalculator.calculateInternalNetworkPath(
//				transformation, request.normalisedPath(), 
//				String.valueOf(options.getPort()), Vers1TransformationManager.HTTP_SCHEME, 
//				request.pathParams());
//		LOGGER.debug("Call URL  (1) " + calculateCallUrl);
//		URL initialContentUrl = new URL(calculateCallUrl);
//		LOGGER.debug("Initial URL  (1) " + initialContentUrl);
//		
//		String initialContent = NetworkFileReader.readNetworkUrlToString(initialContentUrl);
//		LOGGER.debug("Content : " + initialContent);
//		String content = chainTransformations(transformation.getTransformationSteps(), initialContent, request);
//		LOGGER.debug("Content : " + content);
//		writeContent(response, request.request(), content);
//	}

}

