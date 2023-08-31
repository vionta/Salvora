package net.vionta.salvora.config.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "parameter")
public class Parameter implements TransformationParameter {

    @XmlAttribute(name = "key")
    protected String name;
    @XmlAttribute(name = "value")
    protected String value;
    @XmlAttribute(name = "input-port")
    protected Boolean inputPort = Boolean.FALSE;
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public Boolean getInputPort() {
		return inputPort;
	}
	public void setInputPort(Boolean inputPort) {
		this.inputPort = inputPort;
	}
	@Override
	public String toString() {
		return "Parameter [name=" + name + ", value=" + value + ", input-port"+ inputPort+ "]";
	}

}
