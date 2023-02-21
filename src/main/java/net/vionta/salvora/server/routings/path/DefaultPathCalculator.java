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
		
		LOGGER.debug(" Requested Path : "+requestedPath);
		String resultingPath ; 
		
		String calculatedBasePath = null ; 
		String individualRequestedPath = null ; 
		String requestedPathEnd ; 
		
		//The first step is to calculate the base path (if we have one, or two)
		String adjustedBasePath ;
		if(documentReference.getBasePath()!=null) {
			adjustedBasePath = PathParamAdjust.adjustPath(
									documentReference.getBasePath()+1, requestParameters);
			LOGGER.debug(" Adjusted Path : "+adjustedBasePath);
			individualRequestedPath = requestedPath.substring(
										adjustedBasePath.length(), requestedPath.length());
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
			// 
			resultingPath = 
					calculatedBasePath + "/" + individualRequestedPath;
//							requestedPath.substring(calculatedBasePath.length(), 
//									requestedPath.length());
		} else throw new IllegalStateException("Configured Network Path element without Path or base path.Please review salvora cunfiguration.");

		return "./"+resultingPath;
	}

	@Override
	public String calculateInternalNetworkPath(NetworkPathConfiguration documentReference, String requestedPath,
			String port, String scheme, Map<String, String> requestParameters) {
		throw new IllegalStateException("Implementation pending.");

	}

}
