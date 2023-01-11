package net.vionta.xfserver.routings;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.vionta.salvora.config.dto.Trigger;
import net.vionta.salvora.util.xml.XProcRunner;

/**
 * Chain trigger run. 
 */
public class TriggerChainProcces {

	static Logger LOGGER = LoggerFactory.getLogger(TriggerChainProcces.class);

	public static void beforeTriggers(List<Trigger> triggers) {
		for(Trigger trigger: triggers) {
			if(trigger.getBefore()) { runTrigger(trigger); }
		}
	}

	private static void runTrigger(Trigger trigger) {
		try {
			LOGGER.info("running trigger () " + trigger.getName());
			XProcRunner.run(trigger, "");
		} catch (Exception e) {
			LOGGER.error("Failure runnign trigger "+trigger.getName()+" due to ()"+e.getCause());
			e.printStackTrace();
		}
	}

	public static void afterTriggers(List<Trigger> triggers) {
		for(Trigger trigger: triggers) {
			if(!trigger.getBefore()) { runTrigger(trigger); }
		}
	}
	
}
