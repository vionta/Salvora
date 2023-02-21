package net.vionta.salvora.server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import net.vionta.salvora.server.launch.Actions;
import net.vionta.salvora.server.launch.Options;

import static net.vionta.salvora.server.ServerImpl.createAndRunServer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 

/**
 * Main Class, server launcher and main process.
 */
public class Salvora  extends AbstractVerticle {
    
    public static void main(String[] args) throws Exception {
    	Logger log = LoggerFactory.getLogger(Salvora.class);
		log.info("Starting Server");
		if(Actions.checkForHelp(args)) System.exit(0);
		Options options = Actions.getOptions(args);
		Actions.printOptions(options);
		createAndRunServer(options);
		log.info("Server initialized");
    }

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
    	Logger log = LoggerFactory.getLogger(Salvora.class);
    	log.info("Starting Server");
    	Options options = Actions.buildDefaultOptions();
    	Actions.printOptions(options);
    	createAndRunServer(options);
    	log.info("Server initialized");
    }

}
