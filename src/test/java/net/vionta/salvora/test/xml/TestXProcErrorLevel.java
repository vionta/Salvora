package net.vionta.salvora.test.xml;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.vionta.salvora.config.dto.Parameter;
import net.vionta.salvora.config.dto.Trigger;
import net.vionta.salvora.server.request.ParameterCalculation;
import net.vionta.salvora.util.xml.XProcRunner;

class TestXProcErrorLevel {

	static Logger LOGGER = new TestLogger();
	static Logger ACTUAL_LOGGER = LoggerFactory.getLogger(TestXProcRunner.class);

	/**
	 * This test does not work as original class declare its own 
	 * Logger.
	 */
	@Test
	void testXProcOnError() {
		LOGGER.debug(" Running Basic XProc " );
		
		//Defining the trigger.
		Trigger trigger = new Trigger();
		trigger.setName("Trigger de prueba");
		trigger.setParameters(new ArrayList<Parameter>());
		trigger.setSource("src/test/resources/xproc/generate-xproc-error.xpl");
		try {
			XProcRunner.run(trigger, "", ParameterCalculation.buildTransformationParameterList(trigger, null));
			LOGGER.info(" Basic Xproc runnsed " );
		} catch (Exception e) {
			LOGGER.error("Failure runnign trigger "+trigger.getName()+" due to ()"+e.getCause());
			e.printStackTrace();
			fail();
		}	
		ACTUAL_LOGGER.info(((TestLogger)LOGGER).LAST_LEVEL_USED);
//		assertTrue(TestLogger.ERROR_USED);
	}	

}