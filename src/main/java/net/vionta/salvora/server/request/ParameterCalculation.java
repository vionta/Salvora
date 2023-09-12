package net.vionta.salvora.server.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.ext.web.RoutingContext;
import net.vionta.salvora.config.dto.Parameter;
import net.vionta.salvora.config.dto.ParameterInstance;
import net.vionta.salvora.config.dto.ParameterSetElement;
import net.vionta.salvora.config.dto.PathParameter;
import net.vionta.salvora.config.dto.RequestParameter;
import net.vionta.salvora.config.dto.TransformationParameter;
import net.vionta.salvora.server.ServerImpl;
import static net.vionta.salvora.server.routings.path.PathParamAdjust.adjustPath;

public class ParameterCalculation {

	private static Logger LOGGER = LoggerFactory.getLogger(ServerImpl.class);

		/**
	 * Prepares the list of parameters from the step.
	 * 
	 * @param step
	 * @param request
	 * @return The list of parameters for the Xslt Transformation
	 */
	public static List<TransformationParameter> buildTransformationParameterList(ParameterSetElement step, RoutingContext request) {
		
		List<TransformationParameter> transformationParameters = new ArrayList<TransformationParameter>();

		// Handle list of path Parameters
		for (PathParameter param : step.getPathParameters()) {
			String pathParamKey = param.getRequestKey();
			LOGGER.debug("Getting Path Param :" + pathParamKey + " for " + param.getTransformationParamName());
			//get the provided value
			String pathParamValue = request.pathParam(pathParamKey);
			if(param.getValue()!=null && pathParamValue!=null) {
				//adjust the text template value with the provided value
				Map<String, String> adjustParameters = new HashMap<String, String>(1);
				adjustParameters.put(pathParamKey, pathParamValue);
				pathParamValue  = adjustPath(param.getValue(), adjustParameters);
			}
			//We return the suplied or the adjusted parameter, 
			//depending on the case.
			ParameterInstance pathInstance = new ParameterInstance(param, pathParamValue);
			transformationParameters.add(pathInstance);	
		}

		// Handle list of query params
		for (RequestParameter param : step.getRequestParameters()) {
			// Query params have multiple values
			List<String> queryParam = request.queryParam(param.getRequestKey());
			if (queryParam != null && !queryParam.isEmpty()) {
				String queryParamValue = queryParam.get(0);
				if( param.getValue()!=null) {
					Map<String, String> adjustingParameter = new HashMap<String, String>(1);
					adjustingParameter.put(param.getRequestKey(), queryParamValue);
					queryParamValue = adjustPath(param.getValue(), adjustingParameter);
				}
				transformationParameters.add(new ParameterInstance(param, queryParamValue));
			} else if(param.getDefaultValue()!=null) {
				transformationParameters.add(new ParameterInstance(param, param.getDefaultValue()));
			}
		}
			
		// Handle list of simple params
		LOGGER.debug(" Transformation Parameters {} ",transformationParameters);
		for (Parameter param : step.getParameters()) {
			LOGGER.debug(" Params {} -> {} ",param.getName() , param.getValue());
			transformationParameters.add(param);
		}

		return transformationParameters;
	}
	
	
	/**
	 * Prepares the list of parameters from the step.
	 * 
	 * @deprecated
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
			//get the provided value
			String pathParamValue = request.pathParam(pathParamKey);
			if(param.getValue()!=null && pathParamValue!=null) {
				//adjust the text template value with the provided value
				Map<String, String> adjustParameters = new HashMap<String, String>(1);
				adjustParameters.put(pathParamKey, pathParamValue);
				pathParamValue  = adjustPath(param.getValue(), adjustParameters);
			}			
			
			LOGGER.debug("Path Param :" + pathParamKey + " -> " + pathParamValue);
			transformationParameters.put(param.getTransformationParamName(), pathParamValue);
			LOGGER.debug("[" + param.getTransformationParamName() + " -> " + pathParamValue + "]");
		}

		// Handle list of query params
		for (RequestParameter param : step.getRequestParameters()) {
			// Query params have multiple values
			List<String> queryParam = request.queryParam(param.getRequestKey());
			if (queryParam != null && !queryParam.isEmpty()) {
				String queryParamValue = queryParam.get(0);
				if( param.getValue()!=null) {
					Map<String, String> adjustingParameter = new HashMap<String, String>(1);
					adjustingParameter.put(param.getRequestKey(), queryParamValue);
					queryParamValue = adjustPath(param.getValue(), adjustingParameter);
				}
				transformationParameters.put(param.getTransformationParamName(), queryParamValue);			
			} else if(param.getDefaultValue()!=null) {
				transformationParameters.put(param.getTransformationParamName(), param.getDefaultValue());
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
