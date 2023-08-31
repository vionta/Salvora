package net.vionta.salvora.test.file;

import static net.vionta.salvora.server.launch.SalvoraConfigFileInterpreter.readMappingFile;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import com.esotericsoftware.minlog.Log.Logger;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import net.vionta.salvora.config.dto.FileMapping;
import net.vionta.salvora.config.dto.SalvoraApplication;

class ConfigMappingTests {

	@Test
	void configMappingTests() throws TransformerException, SAXException, IOException, ParserConfigurationException {
		String correctionPath = "./src/test/resources/";
		SalvoraApplication readMappingFile2 = readMappingFile(correctionPath+"xmls/sconf/sample.xml");

		assertTrue(readMappingFile2.getFileMappings().size()>2);
	}
	
	
	public static void main(String[] args) {
		SalvoraApplication app  = new SalvoraApplication();
		FileMapping fileMapping = new FileMapping();
		fileMapping.setDescription("Lalala");
		fileMapping.setName("aoeaoea");
		fileMapping.setBasePath("eueouo");
		app.getFileMappings().add(fileMapping);
		 try
	      {
	        //Create JAXB Context
	          JAXBContext jaxbContext = JAXBContext.newInstance(SalvoraApplication.class);
	           
	          //Create Marshaller
	          Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	 
	          //Required formatting??
	          jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	 
	          //Print XML String to Console
	          StringWriter sw = new StringWriter();
	           
	          //Write XML to StringWriter
	          jaxbMarshaller.marshal(app, sw);
	           
	          //Verify XML Content
	          String xmlContent = sw.toString();
	          //Logger.debug( xmlContent );
	 
	      } catch (JAXBException e) {
	          e.printStackTrace();
	      }
	  }
	
	}
