package net.vionta.salvora.test.routes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.vionta.salvora.config.dto.Parameter;
import net.vionta.salvora.config.dto.ParameterSetElement;
import net.vionta.salvora.config.dto.PathParameter;
import net.vionta.salvora.config.dto.RequestParameter;
import net.vionta.salvora.config.dto.TransformationParameter;
import net.vionta.salvora.config.dto.TransformationStep;
import net.vionta.salvora.server.request.ParameterCalculation;
import net.vionta.salvora.test.routes.mock.RoutingContextMock;

/**
 * Tests the param text template adjustments both in Path and Query Params. 
 * 
 */
class ParamTemplateValueAdjustTest {

	private static List<PathParameter> pathParameters;
	private static List<RequestParameter> requestParameters;
	private static RoutingContextMock routingContextMock; 
	ParameterSetElement parameterSet ;
	
	private static Logger LOGGER = LoggerFactory.getLogger(ParamTemplateValueAdjustTest.class);

	@BeforeEach
	void setUpParameterMap() {
		routingContextMock = new RoutingContextMock();
		parameterSet = new TransformationStep();
		((TransformationStep) parameterSet).setPathParameters(new ArrayList<PathParameter>());
		((TransformationStep) parameterSet).setRequestParameters(new ArrayList<RequestParameter>());
		((TransformationStep) parameterSet).setParameters(new ArrayList<Parameter>());
	}


	@Test
	void testAdjustPathParameterList() {
		parameterSet.getPathParameters().add(templatePathParam("topanga", "topanga", "Baliando :topanga",  
				null, false));
		routingContextMock.getHttpServerRequestMock().getMockParameters().put("topanga", "la conga");
		List<TransformationParameter> buildTransformationParameterList = ParameterCalculation.buildTransformationParameterList(parameterSet, routingContextMock);
		String adjustedValue = buildTransformationParameterList.get(0).getValue();
		LOGGER.debug(adjustedValue);
		LOGGER.debug(" " +buildTransformationParameterList);
		assertTrue(adjustedValue.indexOf("la conga")> -1);
		assertEquals(adjustedValue, "Baliando la conga");
	}

	@Test
	void testAdjustQueryParameterList() {
		parameterSet.getRequestParameters().add(templateQueryParam("topanga", "topanga", "Baliando :topanga",  
				null, false));
		routingContextMock.getHttpServerRequestMock().getMockParameters().put("topanga", "la conga");
		List<TransformationParameter> buildTransformationParameterList = ParameterCalculation.buildTransformationParameterList(parameterSet, routingContextMock);
		String adjustedValue = buildTransformationParameterList.get(0).getValue();
		LOGGER.debug(adjustedValue);
		LOGGER.debug(" " +buildTransformationParameterList);
		assertTrue(adjustedValue.indexOf("la conga")> -1);
		assertEquals(adjustedValue, "Baliando la conga");
	}
	
	@Test
	void testAdjustPathParameterMap() {
		parameterSet.getPathParameters().add(templatePathParam("topanga", "topanga", "Baliando :topanga",  
				null, false));
		routingContextMock.getHttpServerRequestMock().getMockParameters().put("topanga", "la conga");
		Map<String, String> buildTransformationParameterMap = ParameterCalculation.buildTransformationParameterMap(parameterSet, routingContextMock);
		String adjustedValue = buildTransformationParameterMap.get("topanga");
		LOGGER.debug(adjustedValue);
		assertTrue(adjustedValue.indexOf("la conga")> -1);
		assertEquals(adjustedValue, "Baliando la conga");
	}

	@Test
	void testAdjustQueryParameterMap() {
		parameterSet.getRequestParameters().add(templateQueryParam("topanga", "topanga", "Baliando :topanga",  
				null, false));
		routingContextMock.getHttpServerRequestMock().getMockParameters().put("topanga", "la conga");
		Map<String, String> buildTransformationParameterMap = ParameterCalculation.buildTransformationParameterMap(parameterSet, routingContextMock);
		//List<TransformationParameter> buildTransformationParameterList = 
		String adjustedValue = buildTransformationParameterMap.get("topanga");
		LOGGER.debug(adjustedValue);
		assertTrue(adjustedValue.indexOf("la conga")> -1);
		assertEquals(adjustedValue, "Baliando la conga");
	}
	
	
	private PathParameter templatePathParam(String key, String transformatioParamName, 
			String value,  String defaultValue,  boolean inputPort) {
		PathParameter pParam = new PathParameter();
		pParam.setValue(value);
		pParam.setRequestKey(key);
		pParam.setDefaultValue(defaultValue);
		pParam.setTransformationParamName(transformatioParamName);
		pParam.setInputPort(inputPort);
		return pParam;
	}

	private RequestParameter templateQueryParam(String key, String transformatioParamName, 
			String value,  String defaultValue,  boolean inputPort) {
		RequestParameter qParam = new RequestParameter();
		qParam.setValue(value);
		qParam.setRequestKey(key);
		qParam.setDefaultValue(defaultValue);
		qParam.setTransformationParamName(transformatioParamName);
		qParam.setInputPort(inputPort);
		return qParam;
	}
	
}
