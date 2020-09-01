package net.vionta.xfserver.launch;

/**
 * Utility class to manage arguments.
 */
public class Arguments {

	/**
	 * Look for an argument on program parameters.
	 * @param args Program parameters
	 * @param arg Argument name to look for
	 * @return The argument value or null if not found.
	 */
	public static String findArgument(String[] args, String arg) {
		boolean found = false;
		String argumentValue = null ; 
		for (int i = 0; i < args.length && !found; i++) {
			if(args[i].lastIndexOf("-"+arg)==0) {
				if(arg.equals("h") ) {
					found = true;
					argumentValue = "help";
				} else {
				found=true;
				argumentValue=args[i].substring(3,args[i].length());
				}
			}
		}
		return argumentValue;
	}

}
