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
public class Transformation implements NetworkPathConfiguration {

	public static final String LOCAL_SOURCE_TYPE = "local_file";
	public static final String LOCAL_DIRECTORY_LISTING= "directory_listing";
	public static final String REMOTE_NETWORK_SOURCE_TYPE = "remote_network";
	public static final String LOCAL_NETWORK_SOURCE_TYPE = "local_network";
	
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "path")
    protected String path;
    @XmlAttribute(name = "base-path")
    protected String basePath;
    @XmlAttribute(name = "base-internal-path")
    protected String baseInternalPath;
    @XmlAttribute(name = "internal-path")
    protected String internalPath;
    
    @XmlElement(name="step", type = TransformationStep.class)
    protected List<TransformationStep> transformationSteps = new ArrayList<TransformationStep>();
    
    @XmlElement(name="trigger", type = Trigger.class)
    protected List<Trigger> triggers = new ArrayList<Trigger>();

    @Override
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String getInternalPath() {
		return internalPath;
	}

	public void setInternalPath(String url) {
		this.internalPath = url;
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

    @Override
	public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String value) {
        this.basePath = value;
    }

    @Override
	public String getBaseInternalPath() {
    	return (baseInternalPath == null) ? getBasePath() : baseInternalPath;
    }

    public void setBaseInternal(String value) {
        this.baseInternalPath = value;
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
		StringBuilder rep = new StringBuilder("Transformation ");
		if(name!=null) rep.append(name);
		rep.append(" : ");
		if(basePath!=null) rep.append(basePath);
		if(path!=null) rep.append(" " ).append(path);
		rep.append(" -> ");
		if(baseInternalPath!=null) rep.append(baseInternalPath);
		if(internalPath!=null) rep.append(" " ).append(internalPath);
		rep.append("\n");
		rep.append(" Steps :").append(transformationSteps.size());
		rep.append(" Triggers :").append(triggers.size());
		return rep.toString();
	}

}
