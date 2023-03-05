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

	public static void beforeTriggers(List<Trigger> triggers, RoutingContext request) {
		for(Trigger trigger: triggers) {
			if(trigger.getBefore()) { runTrigger(trigger, request); }
		}
	}

	private static void runTrigger(Trigger trigger, RoutingContext request) {
		try {
			LOGGER.info("running trigger () " + trigger.getName());
			XProcRunner.run(trigger, "", ParameterCalculation.buildTransformationParameterMap(trigger, request));
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
