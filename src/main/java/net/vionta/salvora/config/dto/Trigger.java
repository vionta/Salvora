package net.vionta.salvora.config.dto;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "trigger")
public class Trigger  implements ParameterSetElement {

	public static final String TRIGGER_TYPE_XPROC ="xproc";
		
    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "source", required = true)
    protected String source;
    @XmlAttribute(name = "input")
    protected String input;
    
    @XmlAttribute(name = "before")
    protected Boolean before = Boolean.FALSE;
    
    @XmlAttribute(name = "type", required = true)
    protected String type = TRIGGER_TYPE_XPROC; 

    @XmlElement(name="path-parameter", type = PathParameter.class)
    protected List<PathParameter> pathParameters = new ArrayList<PathParameter>();
    @XmlElement(name="request-parameter", type = RequestParameter.class)
    protected List<RequestParameter> requestParameters = new ArrayList<RequestParameter>();
    @XmlElement(name="parameter", type = Parameter.class)
    protected List<Parameter> parameters = new ArrayList<Parameter>();

    public String getName() { 	return name; }
	public void setName(String name) { this.name = name; 	}
	public String getSource() {	return source;	}
	public void setSource(String source) {	this.source = source; }
	public Boolean getBefore() { return before;	}
	public void setBefore(Boolean before) { this.before = before; }
	public String getType() {	return type; 	}
	public void setType(String type) {	this.type = type;	}
	
	public String getInput() {	return input;	}
	public void setInput(String input) {	this.input = input;	}
	public List<PathParameter> getPathParameters() {	return pathParameters;		}
	public void setPathParameters(List<PathParameter> pathParameters) {		this.pathParameters = pathParameters;		}
	public List<RequestParameter> getRequestParameters() {		return requestParameters;	}
	public void setRequestParameters(List<RequestParameter> requestParameters) {	this.requestParameters = requestParameters;	}
	public List<Parameter> getParameters() {	return parameters;	}
	public void setParameters(List<Parameter> parameters) {	this.parameters = parameters;	}

}
