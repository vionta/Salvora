package net.vionta.salvora.server.routings.path;


import java.util.Map;

import net.vionta.salvora.config.dto.NetworkPathConfiguration;

public interface IPathCalculator {

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
	String calculateVertxPath(NetworkPathConfiguration documentReference);
	
	/**
	 * Calculates the internal document path for files or documents. 
	 * 
	 * @param documentReference
	 * @param requestedPath
	 * @param requestParameters
	 * @return
	 */
	String calculateInternalPath(NetworkPathConfiguration documentReference, String requestedPath, Map<String, String> requestParameters) ;

	/**
	 * Calculates the internal document path for Network calls (Http).
	 * 
	 * @param documentReference
	 * @param requestedPath
	 * @param port
	 * @param scheme
	 * @param requestParameters
	 * @return
	 */
	String calculateInternalNetworkPath(NetworkPathConfiguration documentReference, String requestedPath, String port, String scheme, Map<String, String> requestParameters); 

}
