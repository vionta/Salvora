package net.vionta.salvora.server;




import static net.vionta.salvora.server.routings.HttpCommandLineRoutings.configureParameterRoutes;
import static net.vionta.salvora.server.routings.HttpRoutings.routeFolderList;
import static net.vionta.salvora.server.routings.HttpRoutings.routeGetMethod;
import static net.vionta.salvora.server.routings.HttpRoutings.routePostMethod;
import static net.vionta.salvora.server.routings.TransformationRoutings.routeTransformation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import net.vionta.salvora.config.dto.FileMapping;
import net.vionta.salvora.config.dto.Transformation;
import net.vionta.salvora.server.launch.Options;

/**
 * Main Server implementation class, Routes and rules.
 */
public class ServerImpl {

	static Logger LOGGER = LoggerFactory.getLogger(ServerImpl.class);

	/**
	 * Main routes and responses definition.
	 * 
	 * @param options Configuration options.
	 */
	protected static void createAndRunServer(Options options) {
		Vertx vertx = Vertx.vertx();
		LOGGER.info("Creating Server");
		HttpServer server = vertx.createHttpServer();
		Router router = Router.router(vertx);
		LOGGER.debug("Configure routes");
		configureRoutes(options, router);
		if(options.isExternalAccess()) {
			server.requestHandler(router).listen(options.getPort());
			LOGGER.info("Server listening on port " + options.getPort() );
		} else {
			server.requestHandler(router).listen(options.getPort(), "localhost");
			LOGGER.info("Server listening on port " + options.getPort()+" - localhost ");
		}
	}

	/**
	 * Configure the vertx routes, used to 
	 * map paths, requests and responses
	 * 
	 * @param options The program call parameters
	 * @param router 
	 */
	private static void configureRoutes(Options options,  Router router) {
		//if the mapping file is provided, we avoid the parameter maps. 
		if(options.getSalvoraApplication() ==null) {
			LOGGER.info(" Configuring parameter routes ");
			configureParameterRoutes(options, router);
		} else {
			//in other case we take the old parameters
			LOGGER.info(" Configuring file routes ");
			configureFileRoutes(options, router);			
		}
	}
	
	/**
	 * Configures the routes according to the 
	 * provided mapping file.
	 * 
	 * @param options program parameters value object. 
	 * @param router
	 */
	private static void configureFileRoutes(Options options, Router router) {
		for(FileMapping fileMapping : options.getSalvoraApplication().getFileMappings() ) {
				LOGGER.info(fileMapping.getDescription());
				LOGGER.info("Routing get path "+fileMapping.getName()+" : "+fileMapping.getBasePath()+" -> "+fileMapping.getBaseInternalPath());
				routeGetMethod(router, fileMapping);
			if(fileMapping.isFileList()) {
				LOGGER.info("Routing List path path : "+fileMapping.getName()+" : "+fileMapping.getBasePath()+" -> "+fileMapping.getBaseInternalPath());
				routeFolderList(router, fileMapping);
			}
			if(fileMapping.isWriteAllowed()) {				
				LOGGER.info("Routing write enabled path : "+fileMapping.getName()+" -> "+fileMapping.getBasePath()+" -> "+fileMapping.getBaseInternalPath());
				routePostMethod(router, fileMapping);
			}
		}
		for(Transformation transformation: options.getSalvoraApplication().getTransformations() ) {
			LOGGER.info(" Loading transformation "+transformation.getName());
			routeTransformation(router, transformation, options);
		}
	}


}
