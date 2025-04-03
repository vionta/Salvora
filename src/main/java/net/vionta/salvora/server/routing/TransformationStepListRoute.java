package net.vionta.salvora.server.routing;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import io.vertx.ext.web.RoutingContext;
import net.vionta.salvora.config.dto.Parameter;
import net.vionta.salvora.config.dto.ParameterSetElement;
import net.vionta.salvora.config.dto.PathParameter;
import net.vionta.salvora.config.dto.RequestParameter;
import net.vionta.salvora.config.dto.TransformationStep;
import net.vionta.salvora.server.request.ParameterCalculation;
import net.vionta.salvora.util.xml.StringReplacementTransform;
import net.vionta.salvora.util.xml.XsltTransform;

public class TransformationStepListRoute {

	private static Logger LOGGER = LoggerFactory.getLogger(TransformationStepListRoute.class);
	
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
	public static String chainTransformations(List<TransformationStep> transformationSteps, String initialContent,
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
	public static String chainNextTransformations(Iterator<TransformationStep> it, String content,
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

	
	/**
	 * Prepares the list of parameters from the step.
	 * 
	 * @param step
	 * @param request
	 * @return The list of parameters for the Xslt Transformation
	 */
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

}
