package net.vionta.salvora.server.routings;




import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import net.vionta.salvora.server.ServerImpl;
import net.vionta.salvora.server.launch.Options;
import net.vionta.salvora.server.response.Response;
import net.vionta.salvora.server.response.contenttype.ContentTypeResolver;
import net.vionta.salvora.util.file.DefaultFileManager;
import static net.vionta.salvora.server.response.Response.close;


/**
 * This class handles the mappings according to 
 * the command line parameters only (mapping file
 * not provided. 
 */
public final class HttpCommandLineRoutings {

	private static Logger LOGGER = LoggerFactory.getLogger(ServerImpl.class);

	/**
	 * Configures the data, forms and xslt parameters. 
	 * 
	 * @param options
	 * @param router
	 */
	public static void configureParameterRoutes(Options options,  Router router) {
		router.route().path("/" + options.getFormsPath() + "/*").method(HttpMethod.GET).handler(request -> {
			sendFile(request);
		});
		router.route().path("/" + options.getXsltformsPath() + "/*").method(HttpMethod.GET).handler(request -> {
			sendFile(request);
		});
		router.route().path("/" + options.getDataPath() + "/*").method(HttpMethod.GET).handler(request -> {
			sendFile(request);
		});
		router.route().handler(BodyHandler.create());
		router.route().path("/" + options.getDataPath() + "/*").method(HttpMethod.POST).method(HttpMethod.PUT)
				.handler(request -> {
					HttpServerResponse response = request.response();
					LOGGER.info("Writting file  : " + request.normalisedPath());
					try {
						Response.contentTypeHeader(response, request.request());
						DefaultFileManager.writeFile(request.normalisedPath(), request.getBodyAsString());
						close(response);
					} catch (IOException e) {
						LOGGER.debug("Post request failure with cause : " + e.getMessage());
						response.setStatusCode(500);
					}
					response.end();
				});
		
		router.route().handler(request -> {
			HttpServerResponse response = request.response();
			// Assign the content type
			response.putHeader("content-type", ContentTypeResolver.resolvePath(request.request().path()));
			response.setStatusCode(404);
			response.end("Your request could not be handled, please review your request. ");
		});
	}

	/**
	 * Reads a file and sends it as a response. 
	 * 
	 * @param request RoutingContext
	 */
	private static void sendFile(RoutingContext request) {
		String url = "./"+request.normalisedPath();
		LOGGER.info("Serving file  : " + url);
		Response.sendFile(request, url);
	}
	
}
