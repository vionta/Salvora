package net.vionta.xfserver;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import net.vionta.xfserver.contenttype.ContentTypeResolver;
import net.vionta.xfserver.file.DefaultFileManager;
import net.vionta.xfserver.launch.Options;

/**
 * Main Server implementation class, Routes and rules.
 *
 */
public class ServerImpl {

	/**
	 * Main routes and responses definition.
	 * 
	 * @param options Configuration options.
	 */
	protected static void createAndRunServer(Options options) {
		Logger logger = LoggerFactory.getLogger(ServerImpl.class);
		Vertx vertx = Vertx.vertx();
		logger.info("Creating Server");
		HttpServer server = vertx.createHttpServer();
		Router router = Router.router(vertx);
		router.route().path("/" + options.getFormsPath() + "/*").method(HttpMethod.GET).handler(request -> {
			HttpServerResponse response = request.response();
			logger.info("Serving file  : " + request.normalisedPath());
			response.putHeader("content-type", ContentTypeResolver.resolvePath(request.request().path()));
			DefaultFileManager.readFileBuffer(request.normalisedPath(), response);
			response.setStatusCode(200);
			response.end();
		});
		router.route().path("/" + options.getXsltformsPath() + "/*").method(HttpMethod.GET).handler(request -> {
			HttpServerResponse response = request.response();
			response.putHeader("content-type", ContentTypeResolver.resolvePath(request.request().path()));
			logger.info("Serving file  : " + request.normalisedPath());
			DefaultFileManager.readFileBuffer(request.normalisedPath(), response);
			response.setStatusCode(200);
			response.end();
		});
		router.route().path("/" + options.getDataPath() + "/*").method(HttpMethod.GET).handler(request -> {
			HttpServerResponse response = request.response();
			logger.info("Serving file  : " + request.normalisedPath());
			response.putHeader("content-type", ContentTypeResolver.resolvePath(request.request().path()));
			DefaultFileManager.readFileBuffer(request.normalisedPath(), response);
			response.setStatusCode(200);
			response.end();
		});
		router.route().handler(BodyHandler.create());
		router.route().path("/" + options.getDataPath() + "/*").method(HttpMethod.POST).method(HttpMethod.PUT)
				.handler(request -> {
					HttpServerResponse response = request.response();
					response.putHeader("content-type", ContentTypeResolver.resolvePath(request.request().path()));
					logger.info("Writting file  : " + request.normalisedPath());
					try {
						DefaultFileManager.writeFile(request.normalisedPath(), request.getBodyAsString());
						response.setStatusCode(200);
					} catch (IOException e) {
						logger.debug("Post request failure with cause : " + e.getMessage());
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
		server.requestHandler(router).listen(options.getPort());
		logger.info("Server listening on port " + options.getPort());
	}

}
