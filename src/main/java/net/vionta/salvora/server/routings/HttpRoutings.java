package net.vionta.salvora.server.routings;



import static net.vionta.salvora.server.response.Response.close;
import static net.vionta.salvora.server.response.Response.contentTypeHeader;
import static net.vionta.salvora.server.response.Response.sendFile;
import static net.vionta.salvora.server.response.Response.writeContent;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import net.vionta.salvora.config.dto.FileMapping;
import net.vionta.salvora.server.ServerImpl;
import net.vionta.salvora.server.response.error.ErrorManager;
import net.vionta.salvora.server.routings.path.DefaultPathCalculator;
import net.vionta.salvora.server.routings.path.IPathCalculator;
import net.vionta.salvora.server.security.PathSecurityChecker;
import net.vionta.salvora.util.file.DefaultFileManager;
import net.vionta.salvora.util.file.FolderList;

/**
 * Utility class to manage Http GET, POST, PUT 
 * methods to their Vertx Routing.
 */
public final class HttpRoutings {

	private static Logger LOGGER = LoggerFactory.getLogger(HttpRoutings.class);

	static IPathCalculator pathCalculator = new DefaultPathCalculator();
	
	public static void routePostMethod(Router router, FileMapping fileMapping) {
		LOGGER.debug("Enabling write path : " +fileMapping.getBasePath() + " : "+fileMapping.getBasePath());
		router.route().handler(BodyHandler.create());
		LOGGER.info("Enabling delete path: " + pathCalculator.calculateVertxPath(fileMapping));

		router.route().path(pathCalculator.calculateVertxPath(fileMapping)).method(HttpMethod.DELETE).handler(request -> {
			LOGGER.info("Deleting file  : " + request.normalisedPath());
			HttpServerResponse response = request.response();
			contentTypeHeader(response, request.request());
			try {
				TriggerChainProcces.beforeTriggers(fileMapping.getTriggers(), request);
				String convertInternalUrl = pathCalculator.calculateInternalPath( fileMapping, request.normalisedPath(), request.pathParams());
				LOGGER.info("File delete path :" + convertInternalUrl );
				DefaultFileManager.deleteFile(convertInternalUrl);
				close(response);
				TriggerChainProcces.afterTriggers(fileMapping.getTriggers(), request);
				} catch (Exception e) {	
					LOGGER.warn("Post request failure with cause : " + e.getCause());
					ErrorManager.notifyError(response, "Post request failure with cause : " + e.getMessage());
				}
			});	
		
		router.route().path(pathCalculator.calculateVertxPath(fileMapping)).method(HttpMethod.POST).method(HttpMethod.PUT)
			.handler(request -> {
				LOGGER.info("Writting file : " + request.normalisedPath());
				HttpServerResponse response = request.response();
				contentTypeHeader(response, request.request());
				try {
					TriggerChainProcces.beforeTriggers(fileMapping.getTriggers(), request);
					String convertInternalUrl = pathCalculator.calculateInternalPath( fileMapping, request.normalisedPath(), request.pathParams());
					DefaultFileManager.writeFile(convertInternalUrl, request.getBodyAsString());
					close(response);
					TriggerChainProcces.afterTriggers(fileMapping.getTriggers(), request);
					} catch (IOException e) {
						LOGGER.warn("Post request failure with cause : " + e.getCause());
						ErrorManager.notifyError(response, "Post request failure with cause : " + e.getMessage());
					}
				});		


	}

	public static void routeGetMethod(Router router, FileMapping fileMapping) {
		LOGGER.debug("Enabling get path");
		router.route().path(pathCalculator.calculateVertxPath(fileMapping)).method(HttpMethod.GET).handler(request -> {
			try {
			LOGGER.info("Serving file  : " + request.normalisedPath());
			String path = pathCalculator.calculateInternalPath(fileMapping, request.normalisedPath(), request.pathParams());
			LOGGER.info("Internal Path: " + path);
				if(DefaultFileManager.fileExists(path)) {	
					
					TriggerChainProcces.beforeTriggers(fileMapping.getTriggers(), request);
					sendFile(request, path);
					TriggerChainProcces.afterTriggers(fileMapping.getTriggers(), request);
				} else {
					ErrorManager.notifyError(request.response(), "File not found", 404);				
				}
			} catch (Exception e) {
				LOGGER.error("Error returning fle", e);
				ErrorManager.notifyError(request.response(), "Error returning fle");
			}
			});
	}

	public static void routeFolderList(Router router, FileMapping fileMapping) {
		LOGGER.debug("Enabling file list path");
		router.route().path(pathCalculator.calculateVertxPath(fileMapping)).method(HttpMethod.GET).handler(request -> {
			HttpServerResponse response = request.response();
			try {
				//TriggerChainProcces.beforeTriggers(fileMapping.getTriggers());
				String folderPath = "./"+fileMapping.getBaseInternalPath() ;
				LOGGER.info("Returning file list : " + folderPath);
				writeContent(response, request.request(), FolderList.printFolderContent(folderPath));
				//TriggerChainProcces.afterTriggers(fileMapping.getTriggers());
			} catch (Exception e) {
				LOGGER.error("Error returning folder list", e);
				ErrorManager.notifyError(response, "Error returning folder list");
			}
		});
	}
	
}
