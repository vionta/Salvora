package net.vionta.xfserver;

import static net.vionta.xfserver.ServerImpl.createAndRunServer;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import net.vionta.xfserver.launch.LauncherActions;
import net.vionta.xfserver.launch.Options;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 

/**
 * Main Class, server launcher and main process.
 */
public class Salvora  extends AbstractVerticle {
    
    public static void main(String[] args) throws Exception {
	Logger log = LoggerFactory.getLogger(Salvora.class);
	log.info("Starting Server");
	if(LauncherActions.checkForHelp(args)) System.exit(0);
	Options options = LauncherActions.getOptions(args);
	LauncherActions.printOptions(options);
	createAndRunServer(options);
	log.info("Server initialized");
	 
    }

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
    	Logger log = LoggerFactory.getLogger(Salvora.class);
    	log.info("Starting Server");
    	Options options = LauncherActions.buildDefaultOptions();
    	LauncherActions.printOptions(options);
    	createAndRunServer(options);
    	log.info("Server initialized");
    }

}
