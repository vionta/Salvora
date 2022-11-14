package net.vionta.salvora.util.file;

import java.io.File;

/**
 * Class that prints the folder file list as an 
 * xml file. 
 */
public final class FolderList {

	public static final String LINE_SEPARATOR = "\n";

	/**
	 * Print Folder Contents.
	 * 
	 * @param folderPath 
	 * @return The folder file contents
	 */
	public static final String printFolderContent(String folderPath) {
		File z = new File(folderPath);
		File[] listFiles = z.listFiles();
		StringBuilder folderContent = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		folderContent.append(LINE_SEPARATOR);
		folderContent.append("<files>");
		folderContent.append(LINE_SEPARATOR);
		for (int i = 0; i < listFiles.length; i++) {
			if(listFiles[i].isDirectory()) {
				folderContent.append("<directory>");
				folderContent.append(listFiles[i].getName());
				folderContent.append("</directory>");
			}
			else {
				folderContent.append("<file>");
				folderContent.append(listFiles[i].getName());
				folderContent.append("</file>");				
			};
			folderContent.append(LINE_SEPARATOR);
		}
		folderContent.append(LINE_SEPARATOR);
		folderContent.append("</files>");
		return folderContent.toString();
	}

}
