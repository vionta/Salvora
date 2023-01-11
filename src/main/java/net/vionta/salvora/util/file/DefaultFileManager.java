package net.vionta.salvora.util.file;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;

import io.vertx.core.http.HttpServerResponse;

/**
 * Utility class that handles file operations on 
 * the host system, read, write and file checks.
 *
 */
public class DefaultFileManager   {

	/**
	 * Checks if a file exists in the defined path.
	 * 
	 * @param path
	 * @return
	 */
	public static boolean fileExists(String path) {
		getLogger(DefaultFileManager.class).info(new File(".").getAbsolutePath());
		return new File("./" +path ).exists();
	}

	/**
	 * Writes a file on the host system.
	 * 
	 * @param path file relative path. 
	 * @param contents File contents. 
	 * @throws IOException Write file exception (permissions, etc.).
	 */
	public static void writeFile(String path, String contents) throws IOException {
		Charset charset = Charset.forName("UTF-8");
		Path filePath = FileSystems.getDefault().getPath(".", path);
		BufferedWriter bufferedWriter = Files.newBufferedWriter(filePath,charset);
		bufferedWriter.write(contents, 0, contents.length());
		bufferedWriter.close();
	}

	/**
	 * Reads a file to the server response.
	 * 
	 * @param path File path.
	 * @param response Servlet response. 
	 */
	public static void readFileBuffer(String path, HttpServerResponse response) {
		Logger logger = getLogger(DefaultFileManager.class);
		  Path filePath = FileSystems.getDefault().getPath(".", path);
			InputStream inputStream;
			try {
				inputStream = Files.newInputStream(filePath);
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				String contents = ""; 
				String line = null;
				logger.info("Writing to the response");
				
				while ((line = bufferedReader.readLine()) != null) {
					logger.debug(line);
//					response.write(line);
					contents+=line;
					contents+="\n";
				}
					response.end(contents);

			} catch (IOException e) {
				logger.error("Writing to the response failed with cause");
				logger.error(e.getMessage());
				e.printStackTrace();
				getLogger(DefaultFileManager.class).warn(e.getMessage());
			}
	}

}