package net.vionta.xfserver.routings;

import io.vertx.core.http.HttpServerResponse;
import net.sf.saxon.functions.MathFunctionSet.Log10Fn;


/**
 * Simple utility class to handle the error 
 * messages.
 */
public class ErrorManager {
	
	/**
	 * Simple method to print error messages to the 
	 * response and the log thread. 
	 * 
	 * @param response
	 * @param message
	 */
	public static void notifyError(HttpServerResponse response, String message) {
		response.write(message);
		response.setStatusCode(500);
		response.end();
	}

}
