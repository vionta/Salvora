package net.vionta.salvora.config.dto;

public class ParameterInstance implements TransformationParameter {

	public ParameterInstance(RequestParameter requestParameter) {
		this.name = requestParameter.getTransformationParamName();
		this.value = requestParameter.getDefaultValue();
		this.inputPort = requestParameter.getInputPort();
	}

	public ParameterInstance(RequestParameter requestParameter, String actualValue) {
		this.name = requestParameter.getTransformationParamName();
		this.value = actualValue;
		this.inputPort = requestParameter.getInputPort();
	}

	public ParameterInstance(PathParameter pathParameter) {
		this.name = pathParameter.getTransformationParamName();
		this.value = pathParameter.getDefaultValue();
		this.inputPort = pathParameter.getInputPort();
	}

	public ParameterInstance(PathParameter pathParameter, String actualValue) {
		this.name = pathParameter.getTransformationParamName();
		this.value = actualValue;
		this.inputPort = pathParameter.getInputPort();
	}
	
	protected String name;		
	protected String value;
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
	
	public String toString() {
		return "Parameter [name=" + name + ", value=" + value + ", input-port"+ inputPort+ "]";
	}

}
