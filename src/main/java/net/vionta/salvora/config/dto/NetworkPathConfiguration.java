package net.vionta.salvora.config.dto;

public interface NetworkPathConfiguration {

	public static final String LOCAL_SOURCE_TYPE = "local_file";
	public static final String LOCAL_DIRECTORY_LISTING= "directory_listing";
	public static final String REMOTE_NETWORK_SOURCE_TYPE = "remote_network";
	
	//No esta utilizandose todavía
	public static final String LOCAL_NETWORK_SOURCE_TYPE = "local_network";

	
	String getPath();

	String getInternalPath();

	String getBasePath();

	String getBaseInternalPath();
	
	String getType();

}