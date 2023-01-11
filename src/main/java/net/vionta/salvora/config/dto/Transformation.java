package net.vionta.salvora.config.dto;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "transformation")
public class Transformation {

	public static final String LOCAL_SOURCE_TYPE = "local_file";
	public static final String LOCAL_DIRECTORY_LISTING= "directory_listing";
	public static final String REMOTE_NETWORK_SOURCE_TYPE = "remote_network";
	
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "path")
    protected String path;
    @XmlAttribute(name = "base-path")
    protected String basePath;
    @XmlAttribute(name = "base-url")
    protected String baseUrl;
    @XmlAttribute(name = "url")
    protected String url;
    
    @XmlElement(name="step", type = TransformationStep.class)
    protected List<TransformationStep> transformationSteps = new ArrayList<TransformationStep>();
    
    @XmlElement(name="trigger", type = Trigger.class)
    protected List<Trigger> triggers = new ArrayList<Trigger>();

    public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@XmlAttribute(name = "type")
    protected String type = "file";


    
    public List<Trigger> getTriggers() {
		return triggers;
	}

	public void setTriggers(List<Trigger> triggers) {
		this.triggers = triggers;
	}

	public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String value) {
        this.basePath = value;
    }

    public String getBaseUrl() {
    	return (baseUrl == null) ? getBasePath() : baseUrl;
    }

    public void setBaseUrl(String value) {
        this.baseUrl = value;
    }

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<TransformationStep> getTransformationSteps() {
		return transformationSteps;
	}

	public void setTransformationSteps(List<TransformationStep> transformationSteps) {
		this.transformationSteps = transformationSteps;
	}

	@Override
	public String toString() {
		return "Transformation [name=" + name + ", path=" + path + ", basePath=" + basePath + ", baseUrl=" + baseUrl
				+ ", url=" + url + ", transformationSteps=" + transformationSteps + ", triggers=" + triggers + ", type="
				+ type + "]";
	}

	
}
