package net.vionta.salvora.server;


import static net.vionta.salvora.server.routings.HttpCommandLineRoutings.configureCmdParameterRoutes;
import static net.vionta.salvora.server.routings.HttpRoutings.routeFolderList;
import static net.vionta.salvora.server.routings.HttpRoutings.routeGetMethod;
import static net.vionta.salvora.server.routings.HttpRoutings.routePostMethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import net.vionta.salvora.server.launch.Options;
import net.vionta.salvora.server.routing.DefaultFileRouterConfigurator;

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
			configureCmdParameterRoutes(options, router);
		} else {
			//in other case we take the old parameters
			LOGGER.info(" Configuring file routes ");
			DefaultFileRouterConfigurator.configureFileRoutes(options, router);			
		}
	}
	
}
