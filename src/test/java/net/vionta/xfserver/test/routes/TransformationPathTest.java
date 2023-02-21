package net.vionta.xfserver.test.routes;

import static net.vionta.salvora.server.routings.path.PathParamAdjust.adjustPath;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Hashtable;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.vionta.salvora.config.dto.PathParameter;
import net.vionta.salvora.config.dto.RequestParameter;
import net.vionta.salvora.config.dto.Transformation;
import net.vionta.salvora.config.dto.TransformationStep;

class TransformationPathTest {

	private static Map<String, String> defaultParameterMap;
	
	private static Logger LOGGER = LoggerFactory.getLogger(TransformationPathTest.class);

	@BeforeEach
	void setUpParameterMap() {
		defaultParameterMap = new Hashtable<String, String>();
		defaultParameterMap.put("entity", "issue");
		defaultParameterMap.put("instancecode","34983274093247");
	}

	@Test
	void testAdjustPathOne() {
		
		TransformationStep step= new TransformationStep();
		RequestParameter rp = new RequestParameter();
		rp.setRequestKey("instancecode");
		rp.setTransformationParamName("INSTANCE_CODE");
		step.getRequestParameters().add(rp);
		PathParameter pp = new PathParameter();
		pp.setRequestKey("entity");
		pp.setTransformationParamName("LOCATION");
		step.getPathParameters().add(pp);
		Transformation transformation = new Transformation();
		transformation.getTransformationSteps().add(step);

		LOGGER.debug("Testig {}", defaultParameterMap);
		
		String initialPath = "form/:entity.xml";
		transformation.setPath(initialPath);

		String targetPath = "form/issue.xml";

		String adjustedPath = adjustPath(initialPath,defaultParameterMap);
		LOGGER.debug("Adjusted Path -{}- ",adjustedPath);
		LOGGER.debug("Target Path -{}-",targetPath);
		assertEquals(adjustedPath, targetPath);
	}

}
