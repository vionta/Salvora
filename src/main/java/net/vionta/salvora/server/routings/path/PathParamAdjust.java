package net.vionta.salvora.server.routings.path;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.vionta.salvora.server.routings.TransformationUrlCalculation;

public class PathParamAdjust {

	private static Logger LOGGER = LoggerFactory.getLogger(PathParamAdjust.class);

	public static String adjustPath(final String path, final Map<String, String> pathParams) {
		LOGGER.debug(" Path {} ", path);
		String adjustedPath = path;
		//While there are parameters.
		while(adjustedPath != null && adjustedPath.indexOf(":")>-1) {
			//find next param 
			String paramName = detectParamName(adjustedPath);
			//update param field
			LOGGER.debug(" replacing {} : {} -> {}", adjustedPath, paramName, pathParams.get(paramName));
			adjustedPath = adjustedPath.replace(":"+paramName, pathParams.get(paramName));
		}
		return adjustedPath;
	}

	private static String detectParamName(String path) {
		//Take the start of the parameter
		LOGGER.debug(" Detecting parameter on {} ", path);
		String pathRest = path.substring(path.indexOf(":")+1, path.length());
		// look for the end of the parameter name
		int nextSlashPosition = (pathRest.indexOf("/") >-1) ? pathRest.indexOf("/")  +1: pathRest.length()+1;
		int nextDotPosition = (pathRest.indexOf(".") >-1) ? pathRest.indexOf(".") +1 : pathRest.length()+1;
		int paramEnd = (nextSlashPosition < nextDotPosition) ? nextSlashPosition : nextDotPosition;
		//Return the parameter name
		String paramName = pathRest.substring(0, paramEnd-1);
		LOGGER.debug(" ParamName {} ", paramName);
		return paramName;
	}
	

}
