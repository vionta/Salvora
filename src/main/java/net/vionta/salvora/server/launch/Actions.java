package net.vionta.salvora.server.launch;

import static net.vionta.salvora.server.launch.SalvoraConfigFileInterpreter.readMappingFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.vionta.salvora.config.dto.SalvoraApplication;

/**
 * Utility class with launch tasks and 
 * utility methods. 
 */
public class Actions {

	static Logger LOGGER = LoggerFactory.getLogger(Actions.class);

	/**
	 * Checks if the user is requesting help 
	 * and acts accordingly. 
	 * 
	 * @param args comand line arguments
	 * @return User request help;
	 */
	public static boolean checkForHelp(String[] args) {
		boolean help = false ;
		String argh = ArgumentsReader.findArgument(args, "h"); 
		if(argh != null )  {
			help = true;
			printHelp();
		}
		return help;
	}

	/**
	 * Prints the application help.
	 */
	private static void printHelp() {
		String HELP_MESSAGE =" Salvora XSLT Form Small Utility \n\n"
	+" Usage \n" 
	+" Jar : java -jar salvora.jar (Params)* \n"
	+" Exe : salvora.exe (params)* \n"
	+" \n"
	+" Params \n"
	+" -h: Show this help message \n"
	+" -f:<path> Form folder path. Default form \n"
	+" -x:<path> XSltForms folder path. Default xsltforms \n"
	+" -f:<path> Data folder path. Default data \n"
	+" -p:<number> Port number (integer). Default 8080 \n"
	+" -e:false Disable external access \n"
	+" -m:<filename> Mapping/configuration file \n"	
	+" \n"
	+" Examples \n"
	+" java -jar salvora.jar \n"
	+ "java -cp salvora.jar net.vionta.xfserver.Salvora  -f:forms -x:xforms -p:8081 \n"
	+ "java -jar salvora-fat-n.n.n.jar -m:simple-mapping.xml -p:8085 \n"
	;
		System.out.print(HELP_MESSAGE);
 	}



	/**
	 * Look for user options and assignement to the process. 
	 * 
	 * @param args Java Main input arguments.
	 * @return A formated Options class with defaults and defined params. 
	 */
	public static Options getOptions(String[] args) {
		Options options = Actions.buildDefaultOptions();
		String formPathParam = ArgumentsReader.findArgument(args, "f");
		if (formPathParam!=null) options.setFormsPath(formPathParam);
		
		String xsltPathParam = ArgumentsReader.findArgument(args, "x");
		if (xsltPathParam!=null) options.setXsltformsPath(xsltPathParam);

		String dataPathParam = ArgumentsReader.findArgument(args, "d");
		if (dataPathParam!=null) options.setDataPath(dataPathParam);

		String port = ArgumentsReader.findArgument(args, "p");
		if (port!=null) options.setPort(Integer.parseInt(port));
		
		String external = ArgumentsReader.findArgument(args, "e");
		if (external !=null && external.equals("false")) options.setExternalAccess(false);

		String mappingFileParam = ArgumentsReader.findArgument(args, "m");
		if (mappingFileParam!=null) {
			LOGGER.debug("Mapping File:"+mappingFileParam );
			options.setMappinFile(mappingFileParam);
			LOGGER.debug("Parsing Mapping File:");
			SalvoraApplication salvoraApplication = readMappingFile(mappingFileParam);		
			LOGGER.debug("Setting Salvora Mapping File:"+salvoraApplication);
			options.setSalvoraApplication(salvoraApplication);
		}
		return options;
	}

	/**
	 * Initialize the options instance with defaults.
	 */
	public static Options buildDefaultOptions() {
		Options options = new Options();  
		options.setFormsPath("form");
		options.setXsltformsPath("xsltforms");
		options.setDataPath("data");
		options.setPort(8080);
		options.setExternalAccess(true);
		return options;
	}

	/**
	 * Prints the application configuration to the console.
	 * @param options
	 */
	public static void printOptions(Options options) {
		System.out.println(options.toString());
	}

}
