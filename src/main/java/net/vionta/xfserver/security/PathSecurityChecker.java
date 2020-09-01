package net.vionta.xfserver.security;

public interface PathSecurityChecker {
	
	/**
	 * Writes a file content to its path. May implement backup in a future. 
	 * 
	 * @param path File path. 
	 * @param Contents File contents as UTF-8 String. 
	 */
	void checkPath(String path);

}
