package net.vionta.xfserver.routings;


import static net.vionta.salvora.util.response.Response.close;
import static net.vionta.salvora.util.response.Response.contentTypeHeader;
import static net.vionta.salvora.util.response.Response.sendFile;
import static net.vionta.salvora.util.response.Response.writeContent;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import net.vionta.salvora.config.dto.FileMapping;
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
				contentTypeHeader(response, request.request());
				try {
					TriggerChainProcces.beforeTriggers(fileMapping.getTriggers());
					String convertInternalUrl = TransformationUrlCalculation.convertInternalUrl(request.normalisedPath(), fileMapping);
					DefaultFileManager.writeFile(convertInternalUrl, request.getBodyAsString());
					close(response);
					TriggerChainProcces.afterTriggers(fileMapping.getTriggers());
					} catch (IOException e) {
						LOGGER.warn("Post request failure with cause : " + e.getCause());
						ErrorManager.notifyError(response, "Post request failure with cause : " + e.getMessage());
					}
				});		
		
		router.route().path("/" + fileMapping.getBasePath() + "/*").method(HttpMethod.DELETE)
		.handler(request -> {
			LOGGER.info("Writting file  : " + request.normalisedPath());
			HttpServerResponse response = request.response();
			contentTypeHeader(response, request.request());
			try {
				TriggerChainProcces.beforeTriggers(fileMapping.getTriggers());
				String convertInternalUrl = TransformationUrlCalculation.convertInternalUrl(request.normalisedPath(), fileMapping);
				DefaultFileManager.deleteFile(convertInternalUrl);
				close(response);
				TriggerChainProcces.afterTriggers(fileMapping.getTriggers());
				} catch (Exception e) {	
					LOGGER.warn("Post request failure with cause : " + e.getCause());
					ErrorManager.notifyError(response, "Post request failure with cause : " + e.getMessage());
				}
			});			
	}

	public static void routeGetMethod(Router router, FileMapping fileMapping) {
		LOGGER.debug("Enabling get path");
		router.route().path("/" + fileMapping.getBasePath() + "/*").method(HttpMethod.GET).handler(request -> {
			LOGGER.info("Serving file  : " + request.normalisedPath());
			String url = "./"+TransformationUrlCalculation.convertInternalUrl(request.normalisedPath(), fileMapping);
			LOGGER.debug(" Resulting url: " + url);
			TriggerChainProcces.beforeTriggers(fileMapping.getTriggers());
			sendFile(request,  url);
			TriggerChainProcces.afterTriggers(fileMapping.getTriggers());
		});
	}

	public static void routeFolderList(Router router, FileMapping fileMapping) {
		LOGGER.debug("Enabling file list path");
		router.route().path("/" + fileMapping.getBasePath()).method(HttpMethod.GET).handler(request -> {
			HttpServerResponse response = request.response();
			try {
//				TriggerChainProcces.beforeTriggers(fileMapping.getTriggers());
				String folderPath = "./"+fileMapping.getBaseUrl();
				LOGGER.info("Returning file list : " + folderPath);
				writeContent(response, request.request(), FolderList.printFolderContent(folderPath));
//				TriggerChainProcces.afterTriggers(fileMapping.getTriggers());
			} catch (Exception e) {
				LOGGER.error("Error returning folder list", e);
				ErrorManager.notifyError(response, "Error returning folder list");
			}
		});
	}
	
}
