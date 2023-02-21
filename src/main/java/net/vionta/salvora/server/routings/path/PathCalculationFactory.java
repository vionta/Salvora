//package net.vionta.salvora.server.routings.path;
//
//import java.util.Map;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import io.vertx.ext.web.Route;
//import net.vionta.salvora.config.dto.FileMapping;
//import net.vionta.salvora.config.dto.NetworkPathConfiguration;
//import net.vionta.salvora.config.dto.Transformation;
//import net.vionta.salvora.server.routings.TransformationUrlCalculation;
//
//public final class PathCalculationFactory {
//
//	/**
//	 * Http scheme used in the server configuration. 
//	 */
//	public static final String HTTP_SCHEME= "http";
//	/**
//	 * Https scheme used in the server configuration. 
//	 */
//	public static final String HTTPS_SCHEME= "http";
//	
//	
//	public static String calculateInternalPath(String requestedPath, NetworkPathConfiguration pathElement, Route route) {
// 	}
//	//Hay que hacer de los dos internos y externos 
//	private static String adjustDeclaredBasePath(String baseInternalPath, String requestedPath) {
//		// TODO Auto-generated method stub
//		if(baseInternalPath.indexOf(":") = -1) return baseInternalPath;
//		return null;
//	}
//
//
//	/**
//	 * Calculates the path/Url that we'll need to use to 
//	 * access the actual file or call 
//	 * 
//	 * @param transformation 
//	 * @param port THe Port number of the server socket. 
//	 * @return Vert.x routing path.
//	 */
//	public static String calculateInternalPath(String requestedPath, Transformation transformation, String port, String scheme) {
//		String url ="";
//		switch (transformation.getType()) {
//			case Transformation.LOCAL_SOURCE_TYPE:
//				if(isSinglePath(transformation)) url = transformation.getInternalPath();
//				else url = convertInternalUrl(requestedPath, transformation);
//				break;
//			case Transformation.LOCAL_DIRECTORY_LISTING:
//				url = transformation.getInternalPath();
//				break;
//			case Transformation.REMOTE_NETWORK_SOURCE_TYPE:
//				throw new IllegalArgumentException(" Not yet implemented :");		
//			//In case the type is not recognized we 
//			// inform of the error. 
//		     default: throw new IllegalArgumentException("Invalid transformation type value :"+transformation.getType());		
//		}
//		return url;
//	}
//
//	/**
//	 * Converts a request call path to the internal definition of 
//	 * the transformation element in the configuration. Basically 
//	 * performs the substitution of the external naming pattern 
//	 * to the internal one.
//	 * 
//	 * @param requestedPath
//	 * @param transformation
//	 * @return
//	 */
//	public static String convertInternalUrl(String requestedPath, NetworkPathConfiguration transformation) {
//		if(isSinglePath(transformation)) return transformation.getInternalPath();
//		else {
//			return transformation.getBaseInternalPath() 
//					+ requestedPath.substring(transformation.getBasePath().length()+1);
//		}
//	}
//
//	/**
//	 * Converts a request call path to the internal definition of 
//	 * the transformation element in the configuration. Basically 
//	 * performs the substitution of the external naming pattern 
//	 * to the internal one.
//	 * 
//	 * @param requestedPath
//	 * @param transformation
//	 * @return
//	 */
//	public static String convertInternalPath(String requestedPath, FileMapping fileMapping) {
//			return fileMapping.getBaseInternalPath() 
//					+ requestedPath.substring(fileMapping.getBasePath().length()+1);
//		
//	}
//
//	public static boolean isSinglePath(NetworkPathConfiguration transformation) {
//		//the transformaiton points to a single file
//		if (transformation.getPath()!= null) {
//			//Checking that the definition is complete
//			return true;
//		// Discarding multi path incomplete definition.
//		} else if (transformation.getBasePath() == null ) {
//			throw new IllegalStateException("The transformation has not a single path, and it also does not have base path and base url.");			
//		// the definition is mulifile
//		} else return false;
//	}
//	
//}
