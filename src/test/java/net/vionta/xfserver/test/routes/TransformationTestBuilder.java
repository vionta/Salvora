package net.vionta.xfserver.test.routes;

import net.vionta.salvora.config.dto.Transformation;

class TransformationTestBuilder {

	static final String TEST_BASE_PATH = "form";
	static final String TEST_BASE_URL = "carpetainterna";
	static final String TEST_PATH= "form/subcarpeta/archivo.xml";
	static final String TEST_URL  = "carpetainterna/subcarpeta/archivo.xml";
	
	protected static Transformation localNetworkTransformation() {
		Transformation t = new Transformation();
		t.setType(Transformation.LOCAL_NETWORK_SOURCE_TYPE);
		return t;
	}
	
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

	protected static Transformation setMultiPath(Transformation t) {
		t.setBasePath(TEST_BASE_PATH);
		t.setBaseUrl(TEST_BASE_URL);
		return t;
	}

	protected static  Transformation setSinglePath(Transformation t) {
		t.setPath(TEST_PATH);
		t.setUrl(TEST_URL);
		return t;
	}
	
	protected static Transformation localSingleNetworkTransformation() {
		return setSinglePath(localNetworkTransformation());
	}
	
	protected static Transformation localSingleTransformation() {
		return setSinglePath(localTransformation());
	}

	protected static Transformation remoteSingleTransformation() {
		return setSinglePath(remoteTransformation());
	}

	protected static Transformation localMultiNetworkTransformation() {
		return setMultiPath(localNetworkTransformation());
	}
	
	protected static Transformation localMultiTransformation() {
		return setMultiPath(localTransformation());
	}
	
	protected static Transformation remoteMultiTransformation() {
		return setMultiPath(remoteTransformation());
	}

}
