package net.vionta.xfserver.routings;

import net.vionta.salvora.config.dto.Transformation;

/**
 * Calculates the file url for the request, 
 * according to the route transformation. 
 *
 */
public class TransformationUrlCalculation {

	/**
	 * Http scheme used in the server configuration. 
	 */
	public static final String HTTP_SCHEME= "http";
	/**
	 * Https scheme used in the server configuration. 
	 */
	public static final String HTTPS_SCHEME= "http";
	
	/**
	 * Calculates the Vert.x route path according to 
	 * the Transformation.
	 * 
	 * @param transformation 
	 * @param port THe Port number of the server socket. 
	 * @return Vert.x routing path.
	 */
	public static String calculateVertxRoute(Transformation transformation) {
		switch (transformation.getType()) {
			case Transformation.LOCAL_SOURCE_TYPE:
				if(isSinglePath(transformation)) return "/"+transformation.getPath();
				else return "/"+transformation.getBasePath()+"*";
//				break;
			case Transformation.LOCAL_NETWORK_SOURCE_TYPE :
				if(isSinglePath(transformation)) return "/"+transformation.getPath();
				else return "/"+transformation.getBasePath()+"*";
//				break;
			case Transformation.REMOTE_NETWORK_SOURCE_TYPE:
				if(isSinglePath(transformation)) return "/"+transformation.getPath();
				else return "/"+transformation.getBasePath()+"*";
//				break;
		     default: throw new IllegalArgumentException("Invalid transformation type value :"+transformation.getType());		
		}
	}

	
	/**
	 * Calculates the path/Url that we'll need to use to 
	 * access the actual file or call 
	 * 
	 * @param transformation 
	 * @param port THe Port number of the server socket. 
	 * @return Vert.x routing path.
	 */
	public static String calculateCallUrl(Transformation transformation, String requestedPath, String port, String scheme) {
		String url ="";
		switch (transformation.getType()) {
			case Transformation.LOCAL_SOURCE_TYPE:
				if(isSinglePath(transformation)) url = transformation.getUrl();
				else url = convertInternalUrl(requestedPath, transformation);
				break;
			case Transformation.LOCAL_NETWORK_SOURCE_TYPE :
				url = scheme+"://localhost:"+port+"/"+requestedPath;
				break;
			case Transformation.REMOTE_NETWORK_SOURCE_TYPE:
//				if(isSinglePath(transformation)) return transformation.getPath();
//				else return transformation.getBasePath()+"*";
				break;
			//In case the type is not recognized we 
			// inform of the error. 
		     default: throw new IllegalArgumentException("Invalid transformation type value :"+transformation.getType());		
		}
		return url;
	}

	/**
	 * Converts a request call path to the internal definition of 
	 * the transformation element in the configuration. Basically 
	 * performs the substitution of the external naming pattern 
	 * to the internal one.
	 * 
	 * @param requestedPath
	 * @param transformation
	 * @return
	 */
	public static String convertInternalUrl(String requestedPath, Transformation transformation) {
		if(isSinglePath(transformation)) return transformation.getUrl();
		else {
			return transformation.getBaseUrl() 
					+ requestedPath.substring(transformation.getBasePath().length()+1);
		}
	}


	/**
	 * Returns if the transformation points to 
	 * a single or multiple path.
	 * 
	 * @param transformation
	 * @return is single path ?
	 */
	public static boolean isSinglePath(Transformation transformation) {
		//the transformaiton points to a single file
		if (transformation.getPath()!= null) {
			//Checking that the definition is complete
			if(transformation.getUrl() == null ) throw new IllegalStateException("The transformation has a single path, but it does not have a single Url.");
			//It is a single path
			return true;
		// Discarding multi path incomplete definition.
		} else if (transformation.getBasePath() == null || transformation.getBaseUrl() == null ) {
			throw new IllegalStateException("The transformation has not a single path, and it also does not have base path and base url.");			
		// the definition is mulifile
		} else return false;
	}
	
}