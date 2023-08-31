package net.vionta.salvora.test.file;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import net.vionta.salvora.util.file.DefaultFileManager;

class DefaultFileManagerTest {

	private static final String TEST_FILE_NAME = "./target/.test-file.txt";
	private static final String TEST_STRING = "Cadena de texto de prueba con acentos, fallará ?";

	@Test
	void testFileExists() {
		Assertions.assertTrue(DefaultFileManager.fileExists("."));
		Assertions.assertFalse(DefaultFileManager.fileExists("./some/very/unusual/path/name.java"));
	}

	@Test
	void testWriteFile() throws IOException {
		DefaultFileManager.writeFile(TEST_FILE_NAME, TEST_STRING);
	}

}
