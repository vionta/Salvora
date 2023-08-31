package net.vionta.salvora.server.response;



import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import net.vionta.salvora.server.ServerImpl;
import net.vionta.salvora.server.response.contenttype.ContentTypeResolver;
import net.vionta.salvora.server.routings.HttpRoutings;

/**
 * Write to response utility methods.
 *
 */
public class Response {

	private static Logger LOGGER = LoggerFactory.getLogger(Response.class);

	public static final void close(HttpServerResponse response, int code) {
		response.setStatusCode(code);
		response.close();
		response.end();
	}

	public static final void close(HttpServerResponse response) {
		close(response, 200);
	}

	public static final void writeContent(HttpServerResponse response, HttpServerRequest request, String content) throws UnsupportedEncodingException {
		LOGGER.info("Writing Content: " + contentHint(content));		
		contentHeaders(response, request, content);
		response.write(content);
		close(response);
	}
	
	public static final void sendFile(RoutingContext request, String url) {
		HttpServerResponse response = request.response();
		contentTypeHeader(response, request.request());
		LOGGER.info("Sending File: " + url);		
		response.sendFile(url);
		LOGGER.info("File Sent: " + url);		
	}
	
	
	/**
	 * Content Length and Content Type Headers
	 * @param response
	 * @param request
	 * @param content
	 * @throws UnsupportedEncodingException 
	 */
	public static final void contentHeaders(HttpServerResponse response, HttpServerRequest request, String content) throws UnsupportedEncodingException {
		LOGGER.debug("Content Lenght :  {} " , Integer.toString(content.getBytes("UTF-8").length));
		response.putHeader("Content-length", Integer.toString(content.getBytes("UTF-8").length));
		contentTypeHeader(response, request);
	}
	
	public static final void contentTypeHeader(HttpServerResponse response, HttpServerRequest request) {
		response.putHeader("content-type", ContentTypeResolver.resolvePath(request.path()));
	}
	
	public final static int REPRESENTATION_LENGTH = 60;
	
	public static final String contentHint(String content) {
		if (content == null || content.isEmpty()) return " Empty content ";
		int l = (content.length() < REPRESENTATION_LENGTH) ? content.length() : REPRESENTATION_LENGTH;
		return content.substring(0, (l - 1));
	}
	
}
