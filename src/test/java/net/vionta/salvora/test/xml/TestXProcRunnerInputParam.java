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

class TestXProcRunnerInputParam {

	static Logger LOGGER = LoggerFactory.getLogger(TestXProcRunnerInputParam.class);
	
	@Test
	void testXProcBasicExecution() {
		LOGGER.debug(" Running Basic XProc " );
		
		//Defining the trigger.
		Trigger trigger = new Trigger();
		trigger.setName("Trigger de prueba");
		trigger.setParameters(new ArrayList<Parameter>());
		trigger.setSource("src/test/resources/xproc/testInputSource.xpl");

		//Setting the input Source
		Parameter parameter = new Parameter();
		parameter.setInputPort(Boolean.TRUE);
		parameter.setName("source");
		parameter.setValue("src/test/resources/mockup/inicio.svg");
		trigger.getParameters().add(parameter);

		LOGGER.debug(" Trigger defined ");
		try {
			XProcRunner.run(trigger, "", ParameterCalculation.buildTransformationParameterList(trigger, null));
			LOGGER.info(" Basic Xproc runnsed " );
		} catch (Exception e) {
			LOGGER.error("Failure runnign trigger "+trigger.getName()+" due to ()"+e.getCause());
			e.printStackTrace();
			fail();
		}	
		assertTrue(new File("target/generated-test-sources/"+"test-input1"+".xml").exists());
	}	

}