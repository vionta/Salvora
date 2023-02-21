package net.vionta.xfserver.file;


import static net.vionta.salvora.util.xml.XsltTransform.transformDocument;
import static net.vionta.salvora.util.xml.XsltTransform.transformDocumentFromContent;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import net.vionta.salvora.server.ServerImpl;

class XsltTransformBasicTests {


	static Logger LOGGER = LoggerFactory.getLogger(ServerImpl.class);

	@Test
	void test() throws TransformerException, SAXException, IOException, ParserConfigurationException {
		String correctionPath = "./src/test/resources/";
		String transformDocumentContent = transformDocument(correctionPath+"xmls/jmeter/jmeter.xml", correctionPath+"xslts/jmeter2html.xsl", null);
		LOGGER.debug(transformDocumentContent);
		assertTrue(transformDocumentContent.indexOf("Consulta de ejemplo")>-1);
	}

	@Test
	void testTransformMultiple() throws TransformerException, SAXException, IOException, ParserConfigurationException {
		String correctionPath = "./src/test/resources/";
		String transformDocumentContent = transformDocument(correctionPath+"xmls/jmeter/jmeter.xml", correctionPath+"xslts/jmeter2html.xsl", null);
		LOGGER.debug(transformDocumentContent);
		transformDocumentContent = transformDocumentFromContent(transformDocumentContent, correctionPath+"xslts/jmeter2html.xsl", null);
		LOGGER.debug(transformDocumentContent);
		assertTrue(transformDocumentContent.indexOf("body>")>-1);
	}

}
