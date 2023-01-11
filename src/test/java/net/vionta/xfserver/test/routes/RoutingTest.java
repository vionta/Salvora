package net.vionta.xfserver.test.routes;


import static net.vionta.xfserver.routings.TransformationUrlCalculation.convertInternalUrl;
import static net.vionta.xfserver.routings.TransformationUrlCalculation.isSinglePath;
import static net.vionta.xfserver.test.routes.TransformationTestBuilder.TEST_PATH;
import static net.vionta.xfserver.test.routes.TransformationTestBuilder.TEST_URL;
import static net.vionta.xfserver.test.routes.TransformationTestBuilder.localMultiNetworkTransformation;
import static net.vionta.xfserver.test.routes.TransformationTestBuilder.localMultiTransformation;
import static net.vionta.xfserver.test.routes.TransformationTestBuilder.localSingleNetworkTransformation;
import static net.vionta.xfserver.test.routes.TransformationTestBuilder.localSingleTransformation;
import static net.vionta.xfserver.test.routes.TransformationTestBuilder.remoteMultiTransformation;
import static net.vionta.xfserver.test.routes.TransformationTestBuilder.remoteSingleTransformation;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.vionta.salvora.config.dto.Transformation;
import net.vionta.xfserver.routings.TransformationUrlCalculation;

class RoutingTest {

	static Logger LOGGER = LoggerFactory.getLogger(RoutingTest.class);

	@Test
	protected void testLocalSingleNetworkTransformation() {
		
		String calculaterdVertxRoute = TransformationUrlCalculation.calculateVertxRoute(t);
		LOGGER.info("Calculated Vertx Route : "+calculaterdVertxRoute);
		assertEquals(calculaterdVertxRoute, "/"+TEST_PATH);
		
		String calculateCallURL= TransformationUrlCalculation.calculateCallUrl(t, TEST_URL,"8085",TransformationUrlCalculation.HTTP_SCHEME);
		LOGGER.info("Calculated CALL : "+t.getUrl()+" -> "+calculateCallURL);
		assertEquals("http://localhost:8085/carpetainterna/subcarpeta/archivo.xml", calculateCallURL);

		calculateCallURL= TransformationUrlCalculation.calculateCallUrl(t, TEST_URL+"#fragment","8085",TransformationUrlCalculation.HTTP_SCHEME);
		LOGGER.info("Calculated CALLx : "+t.getUrl()+" -> "+calculateCallURL);
//		assertEquals("http://localhost:8085/"+TEST_URL+"#fragment", calculateCallURL);
		assertEquals("http://localhost:8085/"+TEST_URL, calculateCallURL);

	}
	
	@Test
	protected void testLocalSingleTransformation() {
		LOGGER.info("Testing Local Single Transformation");
		Transformation t = localSingleTransformation();
			assertTrue(isSinglePath(t));
	}

	@Test
	protected void testRemoteSingleTransformation() {
		LOGGER.info("Testing Single Remote Transformation");
		Transformation t = remoteSingleTransformation();
		assertTrue(isSinglePath(t));
	}

//	@Test
//	protected void testLocalMultiNetworkTransformation() {
//		LOGGER.info("Testing Multi Network Transformation");
//		Transformation t = localMultiNetworkTransformation() ;
//		assertFalse(isSinglePath(t));
//	}

	@Test
	protected void testLocalMultiTransformation() {
		LOGGER.info("Testing Local Multi Transformation");
		Transformation t = localMultiTransformation() ; 
		assertFalse(isSinglePath(t));
	}
	
	@Test
	protected void testRemoteMultiTransformation() {
		LOGGER.info("Testing Remote Multi Transformation");
		Transformation t = remoteMultiTransformation() ;
		assertFalse(isSinglePath(t));
	}

	@Test
	protected void testStringConversion() {
		Transformation t = remoteSingleTransformation() ;
		String urlInterna = convertInternalUrl("/form/carpeta/dos/uno.html?abc#def", t);
		LOGGER.info("Testing Internal URL");
		assertEquals(urlInterna, t.getUrl());

		t = remoteMultiTransformation() ;
		urlInterna = convertInternalUrl("/form/carpeta/dos/uno.html?abc#def", t);
		LOGGER.info("Testing Internal URL");
		assertEquals(urlInterna, "carpetainterna/carpeta/dos/uno.html?abc#def");
		
		t = localMultiTransformation();
		urlInterna = convertInternalUrl("/form/carpeta/dos/uno.html?abc#def", t);
		LOGGER.info("Testing Internal URL");
		assertEquals(urlInterna, "carpetainterna/carpeta/dos/uno.html?abc#def");

		t = localSingleTransformation();
		urlInterna = convertInternalUrl("/form/carpeta/dos/uno.html?abc#def", t);
		LOGGER.info("Testing Internal URL");
		assertEquals(urlInterna, t.getUrl());
		
	}
}
