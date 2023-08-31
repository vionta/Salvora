package net.vionta.salvora.test.file;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.vionta.salvora.server.ServerImpl;
import net.vionta.salvora.util.file.FolderList;

/**
 * Tests the folder list functionality.
 */
class PrintFileFolder {

	static Logger LOGGER = LoggerFactory.getLogger(ServerImpl.class);
	
	@Test
	void testPrintFolderContent() {
		String contents = FolderList.printFolderContent(".");
		LOGGER.debug(contents);
		assertTrue(contents.indexOf("</file>")>-1);
		assertTrue(contents.indexOf("</files>")>-1);
	}

}
