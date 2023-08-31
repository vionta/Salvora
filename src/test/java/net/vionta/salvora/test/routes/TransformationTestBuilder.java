package net.vionta.salvora.test.routes;


import net.vionta.salvora.config.dto.NetworkPathConfiguration;
import net.vionta.salvora.config.dto.Transformation;

class TransformationTestBuilder {

	static final String TEST_BASE_PATH = "form";
	static final String TEST_BASE_URL = "carpetainterna";
	static final String TEST_PATH= "form/subcarpeta/archivo.xml";
	static final String TEST_URL  = "carpetainterna/subcarpeta/archivo.xml";
	
	protected static Transformation localTransformation() {
		Transformation t = new Transformation();
		t.setType(Transformation.LOCAL_SOURCE_TYPE);
		return t;
	}

	protected static Transformation remoteTransformation() {
		Transformation t = new Transformation();
		t.setType(Transformation.REMOTE_NETWORK_SOURCE_TYPE);
		return t;
	}

	protected static NetworkPathConfiguration setMultiPath(Transformation t) {
		t.setBasePath(TEST_BASE_PATH);
		t.setBaseInternal(TEST_BASE_URL);
		return t;
	}

	protected static  NetworkPathConfiguration setSinglePath(Transformation t) {
		t.setPath(TEST_PATH);
		t.setInternalPath(TEST_URL);
		return t;
	}
	
	protected static NetworkPathConfiguration localSingleTransformation() {
		return setSinglePath(localTransformation());
	}

	protected static NetworkPathConfiguration remoteSingleTransformation() {
		return setSinglePath(remoteTransformation());
	}

//	protected static Transformation localMultiNetworkTransformation() {
//		return setMultiPath(localNetworkTransformation());
//	}
	
	protected static NetworkPathConfiguration localMultiTransformation() {
		return setMultiPath(localTransformation());
	}
	
	protected static NetworkPathConfiguration remoteMultiTransformation() {
		return setMultiPath(remoteTransformation());
	}

}
