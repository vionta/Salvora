package net.vionta.salvora.test.routes;

import static net.vionta.salvora.server.routings.path.PathParamAdjust.adjustPath;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Hashtable;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class PathParamAdjustTest {

	private static Map<String, String> defaultParameterMap;
	private static Map<String, String> emptyParameterMap;
	
	private static Logger LOGGER = LoggerFactory.getLogger(PathParamAdjustTest.class);

	@BeforeEach
	void setUpParameterMap() {
		defaultParameterMap = new Hashtable<String, String>();
		defaultParameterMap.put("hola", "radiola");
		defaultParameterMap.put("pera", "limonera");
		defaultParameterMap.put("lirili", "lerele");
		emptyParameterMap = new Hashtable<String, String>();
	}

	@Test
	void testAdjustPathOne() {
		String initialPath = "/hola/:pera/lere/:lirili";
		String targetPath = "/hola/limonera/lere/lerele";
		LOGGER.debug("Testig {}", initialPath);
		String adjustedPath = adjustPath(initialPath,defaultParameterMap);
		LOGGER.debug("Adjusted Path {}",adjustedPath);
		LOGGER.debug("Target Path {}",targetPath);
		assertEquals(adjustedPath, targetPath);
	}
	
	@Test
	void testAdjustPathTwo() {
		String initialPath = "/hola/:lirili/lere/:pera/e";
		String targetPath = "/hola/lerele/lere/limonera/e";
		LOGGER.debug("Testig {}", initialPath);
		String adjustedPath = adjustPath(initialPath,defaultParameterMap);
		LOGGER.debug("Adjusted Path {}",adjustedPath);
		LOGGER.debug("Target Path {}",targetPath);
		assertEquals(adjustedPath, targetPath);
	}
	
	@Test
	void testAdjustPathDot() {
		String initialPath = "/hola/:lirili/lere/:pera.html";
		String targetPath = "/hola/lerele/lere/limonera.html";
		LOGGER.debug("Testig {}", initialPath);
		String adjustedPath = adjustPath(initialPath,defaultParameterMap);
		LOGGER.debug("Adjusted Path {}",adjustedPath);
		LOGGER.debug("Target Path {}",targetPath);
		assertEquals(adjustedPath, targetPath);
	}

	
	@Test
	void testAdjustPathResources() {
		String initialPath = "/resources/xsltforms/xsltforms.xsl";
		String targetPath = "/resources/xsltforms/xsltforms.xsl";
		LOGGER.debug("Testig {}", initialPath);
		String adjustedPath = adjustPath(initialPath,defaultParameterMap);
		LOGGER.debug("Adjusted Path {}",adjustedPath);
		LOGGER.debug("Target Path {}",targetPath);
		assertEquals(adjustedPath, targetPath);
	}
	
}
