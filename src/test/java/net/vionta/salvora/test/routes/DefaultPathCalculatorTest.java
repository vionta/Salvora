package net.vionta.salvora.test.routes;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.vionta.salvora.config.dto.FileMapping;
import net.vionta.salvora.server.routings.path.DefaultPathCalculator;
import net.vionta.salvora.server.routings.path.IPathCalculator;

class DefaultPathCalculatorTest {

	private static Logger LOGGER = LoggerFactory.getLogger(DefaultPathCalculator.class);

	IPathCalculator pc = new DefaultPathCalculator();
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@Test
	void test() {
		FileMapping f = new FileMapping();
		f.setBasePath("index");
		f.setBaseInternalPath("form/index");
		String calculatedPath = pc.calculateInternalPath(f, "index/home.html", null);
		String expectedInternalPath = "./form/index/home.html";
		LOGGER.info("Expected :"+expectedInternalPath+" Calculated :"+calculatedPath);
		assertEquals(expectedInternalPath, calculatedPath);
	}
	
	@Test
	void testTwo() {
		FileMapping f = new FileMapping();
		f.setBasePath("index");
		f.setBaseInternalPath("form/index");
		String calculatedPath = pc.calculateInternalPath(f, "index/home.html", new HashMap<String, String>());
		String expectedInternalPath = "./form/index/home.html";
		LOGGER.info("Expected :"+expectedInternalPath+" Calculated :"+calculatedPath);
		assertEquals(expectedInternalPath, calculatedPath);
	}

	@Test
	void testTransform() {
		FileMapping f = new FileMapping();
		f.setBasePath("issue");
		f.setBaseInternalPath("data/issue");
		String calculatedPath = pc.calculateInternalPath(f, "issue/uno.html", new HashMap<String, String>());
		String expectedInternalPath = "./data/issue/uno.html";
		LOGGER.info("Expected :"+expectedInternalPath+" Calculated :"+calculatedPath);
		assertEquals(expectedInternalPath, calculatedPath);
	}


	@Test
	void testLenth() {
		FileMapping f = new FileMapping();
		f.setBasePath("i");
		f.setBaseInternalPath("data/lolo");
		String calculatedPath = pc.calculateInternalPath(f, "i/uno.html", new HashMap<String, String>());
		String expectedInternalPath = "./data/lolo/uno.html";
		LOGGER.info("Expected :"+expectedInternalPath+" Calculated :"+calculatedPath);
		assertEquals(expectedInternalPath, calculatedPath);
	}
	
	@Test
	void testNewIssueFarm() {
		FileMapping f = new FileMapping();
		f.setBasePath("new");
		f.setBaseInternalPath("form");
		String calculatedPath = pc.calculateInternalPath(f, "new/issue.xml?entity=issue", new HashMap<String, String>());
		String expectedInternalPath = "./form/issue.xml";
		LOGGER.info("Expected :"+expectedInternalPath+" Calculated :"+calculatedPath);
		assertEquals(expectedInternalPath, calculatedPath);
	}

	@Test
	void tesFragment() {
		FileMapping f = new FileMapping();
		f.setBasePath("new");
		f.setBaseInternalPath("form");
		String calculatedPath = pc.calculateInternalPath(f, "new/issue.xml#entity", new HashMap<String, String>());
		String expectedInternalPath = "./form/issue.xml";
		LOGGER.info("Expected :"+expectedInternalPath+" Calculated :"+calculatedPath);
		assertEquals(expectedInternalPath, calculatedPath);
	}
	
	@Test
	void tesFragment1() {
		FileMapping f = new FileMapping();
		f.setBasePath("resources");
		f.setBaseInternalPath("form/resources");
		String calculatedPath = pc.calculateInternalPath(f, "resources/xsltforms/xsltforms.xsl", new HashMap<String, String>());
		String expectedInternalPath = "./form/resources/xsltforms/xsltforms.xsl";
		LOGGER.info("Expected :"+expectedInternalPath+" Calculated :"+calculatedPath);
		assertEquals(expectedInternalPath, calculatedPath);
	}

	@Test
	void tesDelete1() {
		FileMapping f = new FileMapping();
		f.setBasePath("collections/:entity");
		f.setBaseInternalPath("data/:entity");
		HashMap< String , String > params = new HashMap<String, String>();
		params.put("entity", "issue");
		String calculatedPath = pc.calculateInternalPath(f, "/collections/issue/yyyyyyyyyyyyy.xml", params);
		String expectedInternalPath = "./data/issue/yyyyyyyyyyyyy.xml";
		LOGGER.info("Expected :"+expectedInternalPath+" Calculated :"+calculatedPath);
		assertEquals(expectedInternalPath, calculatedPath);
	}

	
	@Test
	void testStringStartsAndEnds()  {
//			System.out.println(detectParamName("collections/:entity") );
//			System.out.println(detectParamName("conf/index/:entity.html")) ;
//		FileMapping f = new FileMapping();
//		f.setBasePath("collections/:entity");
//		f.setBaseInternalPath("data");
//		String calculatedPath = pc.calculateInternalPath(f, "collections/:entity", {"entity", "issue"});
//		String expectedInternalPath = "./data/";
//		LOGGER.info("Expected :"+expectedInternalPath+" Calculated :"+calculatedPath);
//		assertEquals(expectedInternalPath, calculatedPath);
	}
}
