package net.vionta.salvora.server.routing;

import static net.vionta.salvora.server.routing.DefaultRouteBuilder.buildFolderListRoute;
import static net.vionta.salvora.server.routing.DefaultRouteBuilder.buildReadOnlyRoute;
import static net.vionta.salvora.server.routing.DefaultRouteBuilder.buildWriteEnabledRoute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.ext.web.Router;
import net.vionta.salvora.config.dto.FileMapping;
import net.vionta.salvora.server.launch.Options;

public class DefaultFileRouterConfigurator {

	static Logger LOGGER = LoggerFactory.getLogger(DefaultFileRouterConfigurator.class);

	/**
	 * Configures the routes according to the 
	 * provided mapping file.
	 * 
	 * @param options program parameters value object. 
	 * @param router
	 */
	public static void configureFileRoutes(Options options, Router router) {
		for(FileMapping fileMapping : options.getSalvoraApplication().getFileMappings() ) {
				LOGGER.info(fileMapping.getDescription());
				LOGGER.info("Routing get path "+fileMapping.getName()+" : "+fileMapping.getBasePath()+" -> "+fileMapping.getBaseInternalPath());
				buildReadOnlyRoute(router, fileMapping);
			if(fileMapping.isFileList()) {
				LOGGER.info("Routing List path path : "+fileMapping.getName()+" : "+fileMapping.getBasePath()+" -> "+fileMapping.getBaseInternalPath());
				buildFolderListRoute(router, fileMapping);
			}
			if(fileMapping.isWriteAllowed()) {				
				LOGGER.info("Routing write enabled path : "+fileMapping.getName()+" -> "+fileMapping.getBasePath()+" -> "+fileMapping.getBaseInternalPath());
				buildWriteEnabledRoute(router, fileMapping);
			}
		}
	}
	
}
