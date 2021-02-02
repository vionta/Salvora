package net.vionta.xfserver.launch;

/**
 * Utility class with launch tasks and 
 * utility methods. 
 */
public class LauncherActions {

	/**
	 * Checks if the user is requesting help 
	 * and acts accordingly. 
	 * 
	 * @param args
	 * @return User request help;
	 */
	public static boolean checkForHelp(String[] args) {
		boolean help = false ;
		String argh = Arguments.findArgument(args, "h"); 
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
	+" \n"
	+" Example \n"
	+" java -jar salvora.jar \n"
	+ "java -cp salvora.jar net.vionta.xfserver.Salvora  -f:forms -x:xforms -p:8081 \n"
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
		Options options = LauncherActions.buildDefaultOptions();
		String formPathParam = Arguments.findArgument(args, "f");
		if (formPathParam!=null) options.setFormsPath(formPathParam);
		
		String xsltPathParam = Arguments.findArgument(args, "x");
		if (xsltPathParam!=null) options.setXsltformsPath(xsltPathParam);

		String dataPathParam = Arguments.findArgument(args, "d");
		if (dataPathParam!=null) options.setDataPath(dataPathParam);

		String port = Arguments.findArgument(args, "p");
		if (port!=null) options.setPort(Integer.parseInt(port));
		
		String external = Arguments.findArgument(args, "e");
		if (external !=null && external.equals("false")) options.setExternalAccess(false);
		
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
