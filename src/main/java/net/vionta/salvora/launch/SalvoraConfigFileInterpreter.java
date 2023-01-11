package net.vionta.salvora.launch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import net.vionta.salvora.config.dto.SalvoraApplication;

/**
 * Salvora config file Mapping to Java Object 
 * hierarchy.  
 */
public class SalvoraConfigFileInterpreter {

	/**
	 * Reads the file from a file path and gives back 
	 * the Salvora application object representation. 
	 * 
	 * @param configFilePath The file path and name 
	 * @return The Salvora configuration as objects. 
	 * @throws FileNotFoundException
	 */
	public static SalvoraApplication readMappingFile(String configFilePath)  {
		try	{	
			JAXBContext jaxbContext = JAXBContext.newInstance(SalvoraApplication.class);        
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			SalvoraApplication salvoraApplicationConfig = 
				  (SalvoraApplication) jaxbUnmarshaller.unmarshal(new FileReader(new File(configFilePath)));
			System.out.println(salvoraApplicationConfig);
			return salvoraApplicationConfig;
		} catch (FileNotFoundException e) {
			System.out.println("The config file could not be found: "+configFilePath);
			System.out.println("System will exit now");
			System.exit(1);
		} catch (JAXBException e) {
			e.printStackTrace();
			System.out.println("The file could not be processed: "+configFilePath);
			System.out.println("Msg: "+e.getMessage());
			System.out.println("Cause: "+e.getCause());
			System.out.println("System will exit now");
			System.exit(1);
		}
		return null;
	}

}
