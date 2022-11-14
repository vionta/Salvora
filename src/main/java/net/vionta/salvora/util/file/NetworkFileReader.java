package net.vionta.salvora.util.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Utility class to read content from network 
 * file. 
 */
public final class NetworkFileReader {
	
	/**
	 * Reads a file from an http server to 
	 * a String. 
	 *
	 * @param contentUrl
	 * @return The file as an string of content.
	 * @throws IOException
	 */
	public static String  readNetworkUrlToString(URL contentUrl) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(contentUrl.openStream()));
	    StringBuilder content = new StringBuilder();
		String inputLine ;
	    while ((inputLine = in.readLine()) != null) {
	     content.append(inputLine);
	    }
	    in.close();
		return content.toString();
	}	

}
