package net.vionta.xfserver.launch;

/**
 * Options VO class with the possible 
 * user params.
 */
public class Options {

	public Options() {}
	/**
	 * Form folder path.
	 */
	private String formsPath = "form";
	/**
	 * XSLT Forms folder path.
	 */
	private String xsltformsPath = "xsltforms";
	/**
	 * Data folder path.
	 */
	private String dataPath = "data";
	/**
	 * IP port number. 
	 */
	private int port = 8080;
	/**
	 * Allow external access (not yet implemented).
	 */
	private boolean externalAccess = true;
	
	public String getFormsPath() {
		return formsPath;
	}
	
	public void setFormsPath(String formsPath) {
		this.formsPath = formsPath;
	}
	
	public String getXsltformsPath() {
		return xsltformsPath;
	}
	
	public void setXsltformsPath(String xsltformsPath) {
		this.xsltformsPath = xsltformsPath;
	}
	
	public String getDataPath() {
		return dataPath;
	}
	
	public void setDataPath(String dataPath) {
		this.dataPath = dataPath;
	}
	
	public int getPort() {
		return port;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public boolean isExternalAccess() {
		return externalAccess;
	}
	
	public void setExternalAccess(boolean externalAccess) {
		this.externalAccess = externalAccess;
	}
	
	@Override
	public String toString() {
		return "Options: \nformsPath=" + formsPath + " \nxsltformsPath=" + xsltformsPath + " \ndataPath=" + dataPath
				+ "\nport=" + port + " \nexternalAccess=" + externalAccess ;
	}
	
}
