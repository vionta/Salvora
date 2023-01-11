package net.vionta.salvora.util.xml;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import net.vionta.salvora.util.file.NetworkFileReader;

/** 
 * Utility class to deserialize mapping configuration
 * files. 
 */
public class XsltTransform {

	private static Logger LOGGER = LoggerFactory.getLogger(XsltTransform.class);

	/**
	 * Transforms an xml file and returns 
	 * the resulting contents as an string.
	 * 
	 * @param dataFile the data file path. 
	 * @param stylesheetFile the stylesheet file path. 
	 * 
	 * @return The result of the transformation as a string.. 
	 * @throws TransformerException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static String transformDocument(String dataFile, String stylesheetFile, Map<String, String> parameters) throws TransformerException, SAXException, IOException, ParserConfigurationException {
		LOGGER.info("Transforming :"+dataFile+" with "+stylesheetFile);
		Transformer transformer = buildTransformer(stylesheetFile);
		// Add the xslt parameters			
		if(parameters != null && !parameters.isEmpty()) {
			for(Map.Entry<String, String> entry: parameters.entrySet()) {
				transformer.setParameter(entry.getKey(), entry.getValue());
			}
		}
        StringWriter stringWriter = new StringWriter();
        transformer.transform(loadDomSource(dataFile), new StreamResult(stringWriter));
        return stringWriter.toString();
	}

	public static String transformDocumentFromContent(String content, String stylesheetFile, Map<String, String> parameters) throws TransformerException, SAXException, IOException, ParserConfigurationException {
		LOGGER.info("Transforming content with "+stylesheetFile);
		Transformer transformer = buildTransformer(stylesheetFile);
		// Add the xslt parameters			
		if(parameters != null && !parameters.isEmpty()) {
			for(Map.Entry<String, String> entry: parameters.entrySet()) {
				transformer.setParameter(entry.getKey(), entry.getValue());
			}
		}
		StringWriter stringWriter = new StringWriter();
		transformer.transform(loadDomSourceFromContent(content), new StreamResult(stringWriter));
		String result = stringWriter.toString();
		LOGGER.debug("result -> "+result);
		return result ;
	}

	/**
	 * Performs a transformation over a network http 
	 * document. 
	 * 
	 * @param url
	 * @param stylesheetFile
	 * @return
	 * @throws TransformerException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static String transformNetworkDocument(String url, String stylesheetFile) throws TransformerException, SAXException, IOException, ParserConfigurationException  {
		Transformer transformer = buildTransformer(stylesheetFile);
		URL contentUrl = new URL(url);
	    StringWriter stringWriter = new StringWriter();
	    transformer.transform(loadDomSource(NetworkFileReader.readNetworkUrlToString(contentUrl)), new StreamResult(stringWriter));
	    return stringWriter.toString();
	}

	/**
	 * Creates a dom source representation of a string of content
	 * 
	 * @param conent
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static DOMSource loadDomSourceFromContent(String conent)
			throws SAXException, IOException, ParserConfigurationException {
		Document doc = documentBuilder().parse( new InputSource( new StringReader( conent) ) ); 
		DOMSource domSource = new DOMSource(doc);
		return domSource;
	}
	
	private static DOMSource loadDomSource(String dataFile)
			throws SAXException, IOException, ParserConfigurationException {
		DOMSource domSource = new DOMSource(loadXmlDocument(dataFile));
		return domSource;
	}

	private static Transformer buildTransformer(String stylesheetFile)
			throws TransformerFactoryConfigurationError, TransformerConfigurationException {
		Transformer transformer;
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
        StreamSource stylesource = new StreamSource(new File(stylesheetFile));
        transformer = transformerFactory.newTransformer(stylesource);
		return transformer;
	}
	
	/**
	 * Load an xml document from a system data file. 
	 * 
	 * @param dataFile
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	private static Document loadXmlDocument(String dataFile)
			throws SAXException, IOException, ParserConfigurationException {
		//Load the Xml Document    
        Document document = documentBuilder().parse(new File(dataFile));
		return document;
	}

	/**
	 * Utility document builder factory method.
	 * @return
	 * @throws ParserConfigurationException
	 */
	private static DocumentBuilder documentBuilder() throws ParserConfigurationException {
		DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentbuilder = documentFactory.newDocumentBuilder();
		return documentbuilder;
	}

}
