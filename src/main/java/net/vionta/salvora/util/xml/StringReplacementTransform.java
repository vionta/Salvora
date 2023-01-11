package net.vionta.salvora.util.xml;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/** 
 * Utility class replace content with parameters on 
 * files. 
 */
public class StringReplacementTransform {

	private static Logger LOGGER = LoggerFactory.getLogger(StringReplacementTransform.class);

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
	public static String transformDocument(String dataFile, Map<String, String> parameters) throws TransformerException, SAXException, IOException, ParserConfigurationException {
		LOGGER.info("Transforming :"+dataFile);
		LOGGER.info("Parameters :"+parameters);
	    StringBuilder builder = new StringBuilder();
	    BufferedReader buffer = new BufferedReader(new FileReader(dataFile));
	    String str;
           while ((str = buffer.readLine()) != null) {
               builder.append(str).append("\n");
           }
		String fileContent =  builder.toString() ;
		if(parameters != null && !parameters.isEmpty()) {
			for(Map.Entry<String, String> entry: parameters.entrySet()) {
				LOGGER.info("Replacing  :"+entry.getKey() + " -> "+entry.getValue());
				fileContent= fileContent.replaceAll(entry.getKey(), entry.getValue());
			}
		}
		LOGGER.debug("Transformed :"+fileContent);
        return fileContent;
	}

	public static String transformDocumentFromContent(String content, Map<String, String> parameters) throws TransformerException, SAXException, IOException, ParserConfigurationException {
		LOGGER.info("Transforming content ");
		if(parameters != null && !parameters.isEmpty()) {
			for(Map.Entry<String, String> entry: parameters.entrySet()) {
				content.replaceAll(entry.getKey(), entry.getValue());
			}
		}
		LOGGER.debug("result -> "+content);
		return content;
	}

}
