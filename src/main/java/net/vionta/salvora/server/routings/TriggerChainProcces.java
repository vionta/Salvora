package net.vionta.salvora.server.routings;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.ext.web.RoutingContext;
import net.vionta.salvora.config.dto.Trigger;
import net.vionta.salvora.server.request.ParameterCalculation;
import net.vionta.salvora.util.xml.XProcRunner;

/**
 * Chain trigger run. 
 */
public class TriggerChainProcces {

	static Logger LOGGER = LoggerFactory.getLogger(TriggerChainProcces.class);
	static Logger TRIGGER_LOGGER = LoggerFactory.getLogger(net.vionta.salvora.server.routing.log.TriggerRouting.class);

	public static void beforeTriggers(List<Trigger> triggers, RoutingContext request) {
		
		LOGGER.info("Before Triggers: " + String.valueOf( triggers != null));
		if(triggers!=null) LOGGER.info(" Size "+triggers.size());
		for(Trigger trigger: triggers) {			
			LOGGER.info("Trigger : " + trigger.getName() +" - Before: "+trigger.getBefore());
			if(trigger.getBefore()) { runTrigger(trigger, request); }
		}
	}

	private static void runTrigger(Trigger trigger, RoutingContext request) {
		try {
			LOGGER.info("running trigger () " + trigger.getName());
			TRIGGER_LOGGER.info("running trigger () " + trigger.getName());
			XProcRunner.run(trigger, "", ParameterCalculation.buildTransformationParameterList(trigger, request));
		} catch (Exception e) {
			LOGGER.error("Failure runnign trigger "+trigger.getName()+" due to ()"+e.getCause());
			e.printStackTrace();
		}
	}

	public static void afterTriggers(List<Trigger> triggers, RoutingContext request) {
		for(Trigger trigger: triggers) {
			if(!trigger.getBefore()) { runTrigger(trigger, request); }
		}
	}
	
}
