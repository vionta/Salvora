package net.vionta.salvora.server.request;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.ext.web.RoutingContext;
import net.vionta.salvora.config.dto.Parameter;
import net.vionta.salvora.config.dto.ParameterSetElement;
import net.vionta.salvora.config.dto.PathParameter;
import net.vionta.salvora.config.dto.RequestParameter;
import net.vionta.salvora.server.ServerImpl;

public class ParameterCalculation {

	private static Logger LOGGER = LoggerFactory.getLogger(ServerImpl.class);

	/**
	 * Prepares the list of parameters from the step.
	 * 
	 * @param step
	 * @param request
	 * @return The list of parameters for the Xslt Transformation
	 */
	public static Map<String, String> buildTransformationParameterMap(ParameterSetElement step, RoutingContext request) {
		
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
