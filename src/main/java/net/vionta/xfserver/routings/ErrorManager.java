package net.vionta.xfserver.routings;

import io.vertx.core.http.HttpServerResponse;
import static net.vionta.salvora.util.response.Response.close;


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
		close(response, 500);
	}

}
