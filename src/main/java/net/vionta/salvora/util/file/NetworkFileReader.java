package net.vionta.salvora.util.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.vionta.salvora.server.ServerImpl;

/**
 * Utility class to read content from network 
 * file. 
 */
public final class NetworkFileReader {

	private static Logger LOGGER = LoggerFactory.getLogger(NetworkFileReader.class);

	/**
	 * Reads a file from an http server to 
	 * a String. 
	 *
	 * @param contentUrl
	 * @return The file as an string of content.
	 * @throws IOException
	 */
	public static String  readNetworkUrlToString(URL contentUrl) throws IOException {
		LOGGER.debug("Leyendo " + contentUrl);
		BufferedReader in = new BufferedReader(new InputStreamReader(contentUrl.openStream()));
	    StringBuilder content = new StringBuilder();
		String inputLine ;
	    while ((inputLine = in.readLine()) != null) {
	    	LOGGER.debug(inputLine);
	    	content.append(inputLine);
	    }
	    in.close();
	    LOGGER.debug("Devolviendo "+content.substring(1,30));
		return content.toString();
	}	
	
	public static void main(String[] args) throws MalformedURLException, IOException {
		URL url = new URL("http://localhost:8085/issues");
		LOGGER.debug(NetworkFileReader.readNetworkUrlToString(url));
	}

}
