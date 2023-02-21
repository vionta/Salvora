package net.vionta.salvora.server.routings;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.vionta.salvora.config.dto.FileMapping;
import net.vionta.salvora.config.dto.NetworkPathConfiguration;
import net.vionta.salvora.config.dto.Transformation;
import net.vionta.salvora.server.routings.path.DefaultPathCalculator;
import net.vionta.salvora.server.routings.path.IPathCalculator;

/**
 * Calculates the file url for the request, 
 * according to the route transformation. 
 *
 */
@Deprecated
public class TransformationUrlCalculation {

	/**
	 * Http scheme used in the server configuration. 
	 */
	public static final String HTTP_SCHEME= "http";
	/**
	 * Https scheme used in the server configuration. 
	 */
	public static final String HTTPS_SCHEME= "http";
	
	private static Logger LOGGER = LoggerFactory.getLogger(TransformationUrlCalculation.class);
	
	static IPathCalculator pathCalculator = new DefaultPathCalculator();

	/**
	 * Calculates the Vert.x route path according to 
	 * the Transformation.
	 * 
	 * @param transformation 
	 * @param port THe Port number of the server socket. 
	 * @return Vert.x routing path.
	 */
	public static String calculateVertxRoute(Transformation transformation) {
		return pathCalculator.calculateVertxPath(transformation);
	}
	
	/**
	 * Calculates the path/Url that we'll need to use to 
	 * access the actual file or call 
	 * 
	 * @param transformation 
	 * @param port THe Port number of the server socket. 
	 * @return Vert.x routing path.
	 */
	public static String calculateCallUrl(Transformation transformation, String requestedPath, String port, String scheme, Map<String, String> pathParameters) {
		String url ="";
		switch (transformation.getType()) {
			
			case Transformation.LOCAL_SOURCE_TYPE:
				url = pathCalculator.calculateInternalPath(transformation, requestedPath, pathParameters);
				break;
			case Transformation.LOCAL_DIRECTORY_LISTING:
				url = pathCalculator.calculateInternalPath(transformation, requestedPath, pathParameters);
				break;
			case Transformation.REMOTE_NETWORK_SOURCE_TYPE:
				url = pathCalculator.calculateInternalNetworkPath(transformation, requestedPath, port, scheme, pathParameters);
		     default: throw new IllegalArgumentException("Invalid transformation type value :"+transformation.getType());		
		}
		return url;
	}

}