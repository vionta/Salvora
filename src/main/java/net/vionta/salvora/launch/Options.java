package net.vionta.salvora.launch;

import net.vionta.salvora.config.dto.SalvoraApplication;

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
	 * Mapping file.
	 */
	private String mappingFile ;
	/**
	 * IP port number. 
	 */
	private int port = 8080;
	/**
	 * Allow external access (not yet implemented).
	 */
	private boolean externalAccess = false;
	
	private SalvoraApplication salvoraApplication;
	
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
		return "Options [formsPath=" + formsPath + ", xsltformsPath=" + xsltformsPath + ", dataPath=" + dataPath
				+ ", mappingFile=" + mappingFile + ", port=" + port + ", externalAccess=" + externalAccess
				+ ", salvoraApplication=" + salvoraApplication + "]";
	}

	public SalvoraApplication getSalvoraApplication() {
		return salvoraApplication;
	}

	public void setSalvoraApplication(SalvoraApplication salvoraApplication) {
		this.salvoraApplication = salvoraApplication;
	}

	public void setMappinFile(String mappingFileParam) {
		this.mappingFile=mappingFileParam;		
	}

	public String getMappingFile() {
		return mappingFile;
	}
	
}
