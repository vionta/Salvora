package net.vionta.salvora.config.dto;

import java.util.List;

public interface ParameterSetElement {

	List<PathParameter> getPathParameters();

	List<RequestParameter> getRequestParameters();

	List<Parameter> getParameters();

}