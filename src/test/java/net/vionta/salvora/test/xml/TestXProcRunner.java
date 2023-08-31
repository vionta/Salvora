package net.vionta.salvora.test.xml;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

class TestXProcRunner {

	static Logger LOGGER = LoggerFactory.getLogger(TestXProcRunner.class);

	@Test
	void testRouteCalculation() {
			LOGGER.warn(" This test needs adjustment based on the local path where it is running");
			String testUrl = "hola.xpl";
			String expectedResult = "file:///"+ System.getProperty("user.dir").replace("\\", "/") +"/hola.xpl";
			LOGGER.debug(" expected" +expectedResult);
			LOGGER.debug(" Response " +XProcRunner.calculateLocalFileAbsolutePath(testUrl));
			LOGGER.debug(" > " +XProcRunner.calculateLocalFileAbsolutePath(testUrl).equals(expectedResult));
			assertEquals(expectedResult, XProcRunner.calculateLocalFileAbsolutePath(testUrl));
	}

	
	@Test
	void testXProcBasicExecution() {
		LOGGER.debug(" Running Basic XProc " );
		Trigger trigger = new Trigger();
		trigger.setName("Trigger de prueba");
		trigger.setParameters(new ArrayList<Parameter>());
		trigger.setSource("src/test/resources/xproc/testgenmockup.xpl");
		LOGGER.debug(" Trigger defined ");
		try {
			XProcRunner.run(trigger, "", ParameterCalculation.buildTransformationParameterList(trigger, null));
			LOGGER.info(" Basic Xproc runnsed " );
		} catch (Exception e) {
			LOGGER.error("Failure runnign trigger "+trigger.getName()+" due to ()"+e.getCause());
			e.printStackTrace();
			fail();
		}	
		assertTrue(new File("src/test/resources/"+"test-out_mainform"+".svg").exists());
	}	

}