package net.vionta.xfserver.routings;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import net.vionta.salvora.config.dto.FileMapping;
import net.vionta.salvora.util.contenttype.ContentTypeResolver;
import net.vionta.salvora.util.file.DefaultFileManager;
import net.vionta.salvora.util.file.FolderList;
import net.vionta.xfserver.ServerImpl;

/**
 * Utility class to manage Http GET, POST, PUT 
 * methods to their Vertx Routing.
 */
public final class HttpRoutings {

	private static Logger LOGGER = LoggerFactory.getLogger(ServerImpl.class);

	public static void routePostMethod(Router router, FileMapping fileMapping) {
		LOGGER.debug("Enabling write path : " +fileMapping.getBasePath());
		router.route().handler(BodyHandler.create());
		router.route().path("/" + fileMapping.getBasePath() + "/*").method(HttpMethod.POST).method(HttpMethod.PUT)
			.handler(request -> {
				LOGGER.info("Writting file  : " + request.normalisedPath());
				HttpServerResponse response = request.response();
				response.putHeader("content-type", ContentTypeResolver.resolvePath(request.request().path()));
				try {
					DefaultFileManager.writeFile(request.normalisedPath(), request.getBodyAsString());
					response.setStatusCode(200);
					response.end();
					} catch (IOException e) {
						LOGGER.warn("Post request failure with cause : " + e.getMessage());
						ErrorManager.notifyError(response, "Post request failure with cause : " + e.getMessage());
					}
				});			
	}

	public static void routeGetMethod(Router router, FileMapping fileMapping) {
		LOGGER.debug("Enabling get path");
		router.route().path("/" + fileMapping.getBasePath() + "/*").method(HttpMethod.GET).handler(request -> {
			LOGGER.info("Serving file  : " + request.normalisedPath());
			HttpServerResponse response = request.response();
			response.putHeader("Content-type", ContentTypeResolver.resolvePath(request.request().path()));
			response.sendFile("./"+request.normalisedPath());
			response.setStatusCode(200);
			response.end();
		});
	}

	public static void routeFolderList(Router router, FileMapping fileMapping) {
		LOGGER.debug("Enabling file list path");
		router.route().path("/" + fileMapping.getBasePath()).method(HttpMethod.GET).handler(request -> {
			HttpServerResponse response = request.response();
			try {
				String folderPath = "./"+fileMapping.getBasePath();
				LOGGER.info("Returning file list : " + folderPath);
				String printFolderContent = FolderList.printFolderContent(folderPath);
				response.putHeader("Content-length", Integer.toString(printFolderContent.getBytes().length));
				response.putHeader("Content-type", ContentTypeResolver.resolvePath(request.request().path()));
				response.write(printFolderContent);
				LOGGER.info("File list : " + printFolderContent);
				response.setStatusCode(200);
				response.end();
			} catch (Exception e) {
				LOGGER.error("Error returning folder list", e);
				ErrorManager.notifyError(response, "Error returning folder list");
			}
		});
	}
	
}
