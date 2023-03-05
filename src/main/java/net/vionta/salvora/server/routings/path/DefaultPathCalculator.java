package net.vionta.salvora.server.routings.path;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.vionta.salvora.config.dto.NetworkPathConfiguration;

public class DefaultPathCalculator implements IPathCalculator {

	private static Logger LOGGER = LoggerFactory.getLogger(IPathCalculator.class);

	@Override
	public String calculateVertxPath(NetworkPathConfiguration documentReference) {
		StringBuilder calculatedVertxRoutePath = new StringBuilder();
		//If we have a base path we use it as a start.
		if(documentReference.getBasePath()!=null) calculatedVertxRoutePath.append("/").append(documentReference.getBasePath());
		//if a single path is declared, we add it. else we add the wildcard to perform matching.
		if(documentReference.getPath()!=null) calculatedVertxRoutePath.append("/").append(documentReference.getPath());
		else calculatedVertxRoutePath.append("/*");
		return calculatedVertxRoutePath.toString();
	}

	@Override
	public String calculateInternalPath(NetworkPathConfiguration documentReference, String requestedPath,
			Map<String, String> requestParameters) {
		
		String resultingPath ; 

		LOGGER.debug(" Requested Path : "+requestedPath);

		
		String calculatedBasePath = null ; 
		String individualRequestedPath = null ; 
		String requestedPathEnd ; 
		
		//The first step is to calculate the base path (if we have  one, or two)
		String adjustedBasePath ;
		if(documentReference.getBasePath()!=null) {

			String adjustedRequestedPath = PathParamAdjust.adjustPath(
					requestedPath, requestParameters);
			LOGGER.debug(" Adjusted Requested Path : "+requestedPath);
			
			adjustedBasePath = PathParamAdjust.adjustPath(
									documentReference.getBasePath(), requestParameters);
			
			LOGGER.debug(" Adjusted Path : "+adjustedBasePath);
			individualRequestedPath = adjustedRequestedPath.substring(
										adjustedBasePath.length()+1, adjustedRequestedPath.length());
			if(individualRequestedPath.startsWith("/")) individualRequestedPath = individualRequestedPath.substring(1);
			LOGGER.debug(" Individual Requested Path : "+individualRequestedPath);
			
			  if(individualRequestedPath.indexOf("?") > -1)  individualRequestedPath = individualRequestedPath.substring(
					0, individualRequestedPath.indexOf("?"));
			  if(individualRequestedPath.indexOf("#") > -1)  individualRequestedPath = individualRequestedPath.substring(
					  0, individualRequestedPath.indexOf("#"));
			if(documentReference.getBaseInternalPath() !=null) {
				calculatedBasePath = PathParamAdjust.adjustPath(
										documentReference.getBaseInternalPath(), requestParameters);
			} else calculatedBasePath = adjustedBasePath;
		} 
		LOGGER.debug(" Calculated Base Path "+calculatedBasePath);
		LOGGER.debug(" Calculated Path : "+calculatedBasePath);
		LOGGER.debug(" individualRequestedPath : "+individualRequestedPath);

		//Last part is adjusting the document call.
		if(documentReference.getInternalPath()!=null) {
			String adjustedInternalPath = 
					PathParamAdjust.adjustPath(documentReference.getInternalPath(), 
							requestParameters);
			resultingPath = (calculatedBasePath != null) ? 
					calculatedBasePath +"/"+ adjustedInternalPath : adjustedInternalPath ;
		} else if(documentReference.getPath() != null) {
			String requesPathPart = 
					PathParamAdjust.adjustPath(documentReference.getPath(), requestParameters);
			resultingPath = (calculatedBasePath != null ) ? 
					calculatedBasePath+"/"+requesPathPart : requesPathPart ;
		} else if(calculatedBasePath != null) {
			resultingPath = 
					calculatedBasePath + "/" + individualRequestedPath;
		} else throw new IllegalStateException("Configured Network Path element without Path or base path.Please review salvora cunfiguration.");

		return "./"+resultingPath;
	}

	@Override
	public String calculateInternalNetworkPath(NetworkPathConfiguration documentReference, String requestedPath,
			String port, String scheme, Map<String, String> requestParameters) {
		throw new IllegalStateException("Implementation pending.");

	}

	public static void main(String[] args) {
		System.out.println("abcde".substring(0,"abcde".length()));
		System.out.println("abcde".length());
		System.out.println("abcde".substring(1,"abcde".length()));
	}
}
