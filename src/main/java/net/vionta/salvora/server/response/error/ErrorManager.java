package net.vionta.salvora.server.response.error;


import io.vertx.core.http.HttpServerResponse;
import net.vionta.salvora.server.ServerImpl;

import static net.vionta.salvora.server.response.Response.close;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Simple utility class to handle the error 
 * messages.
 */
public class ErrorManager {
	
	private static Logger LOGGER = LoggerFactory.getLogger(ErrorManager.class);

	/**
	 * Simple method to print error messages to the 
	 * response and the log thread. 
	 * 
	 * @param response
	 * @param message
	 */
	public static void notifyError(HttpServerResponse response, String message) {
		notifyError(response, message, 500);
	}

	/**
	 * Simple method to print error messages to the 
	 * response and the log thread. 
	 * 
	 * @param response
	 * @param message
	 */
	public static void notifyError(HttpServerResponse response, String message, int errorCode) {
		LOGGER.warn(message);
		response.putHeader("Content-length", "0");
		response.setStatusCode(errorCode);
		response.write(message);
		response.setStatusCode(errorCode);
		close(response, errorCode);
	}
	
	public static void logError(HttpServerResponse response, String message) {
		LOGGER.warn(message);
	}
	

}
