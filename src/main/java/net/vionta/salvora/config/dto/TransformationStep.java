package net.vionta.salvora.config.dto;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "step")
public class TransformationStep {

    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "source", required = true)
    protected String source;
    @XmlAttribute(name = "type", required = true)
    protected String type;

    @XmlElement(name="path-parameter", type = PathParameter.class)
    protected List<PathParameter> pathParameters = new ArrayList<PathParameter>();
    @XmlElement(name="request-parameter", type = RequestParameter.class)
    protected List<RequestParameter> requestParameters = new ArrayList<RequestParameter>();

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String value) {
        this.source = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String value) {
        this.type = value;
    }

	public List<PathParameter> getPathParameters() {
		return pathParameters;
	}

	public void setPathParameters(List<PathParameter> pathParameters) {
		this.pathParameters = pathParameters;
	}

	public List<RequestParameter> getRequestParameters() {
		return requestParameters;
	}

	public void setRequestParameters(List<RequestParameter> requestParameters) {
		this.requestParameters = requestParameters;
	}

	@Override
	public String toString() {
		return "TransformationStep [name=" + name + ", source=" + source + ", type=" + type + ", pathParameters="
				+ pathParameters + ", requestParameters=" + requestParameters + "]";
	}
	
}