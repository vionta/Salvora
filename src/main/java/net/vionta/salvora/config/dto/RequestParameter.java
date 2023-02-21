package net.vionta.salvora.config.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "request-parameter")
public class RequestParameter {

    @XmlAttribute(name = "key")
    protected String requestKey;
    @XmlAttribute(name = "transformation-param-name")
    protected String transformationParamName;

    @XmlAttribute(name = "default")
    protected String defaultValue;

	public String getRequestKey() {
        return requestKey;
    }

    public void setRequestKey(String value) {
        this.requestKey = value;
    }

    public String getTransformationParamName() {
        return transformationParamName;
    }

    public void setTransformationParamName(String value) {
        this.transformationParamName = value;
    }

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	public String toString() {
		return "RequestParameter [requestKey=" + requestKey + ", transformationParamName=" + transformationParamName
				+ "]";
	}
    
}
