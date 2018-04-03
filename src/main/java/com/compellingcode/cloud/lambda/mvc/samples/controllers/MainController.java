package com.compellingcode.cloud.lambda.mvc.samples.controllers;

import java.util.HashMap;
import java.util.Map;

import com.compellingcode.cloud.lambda.mvc.endpoint.Endpoint;
import com.compellingcode.cloud.lambda.mvc.endpoint.EndpointParameter;
import com.compellingcode.cloud.lambda.mvc.endpoint.ParameterType;
import com.compellingcode.cloud.lambda.mvc.endpoint.RequestMethod;
import com.compellingcode.cloud.lambda.mvc.view.ClasspathResourceLambdaResponse;
import com.compellingcode.cloud.lambda.mvc.view.HtmlLambdaResponse;
import com.compellingcode.cloud.lambda.mvc.view.JSONLambdaResponse;
import com.compellingcode.cloud.lambda.mvc.view.LambdaResponse;

public class MainController {

	@Endpoint(value={"/test", "/"}, method=RequestMethod.GET)
	public LambdaResponse test() {
		return new HtmlLambdaResponse("hello.tpl");
	}
	
	@Endpoint({"/getid/{id}"})
	public LambdaResponse getId(@EndpointParameter(type=ParameterType.PATH_PARAMETER, name="id") int id,
			                    @EndpointParameter(type=ParameterType.IP) String ip) {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("id", id);
		values.put("ip", ip);
		
		return new JSONLambdaResponse(values);
	}
	
	@Endpoint({"/image"})
	public LambdaResponse image() {
		return new ClasspathResourceLambdaResponse("images/minions.jpg", true);
	}
	
}
