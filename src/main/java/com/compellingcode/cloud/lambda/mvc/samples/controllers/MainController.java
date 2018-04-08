package com.compellingcode.cloud.lambda.mvc.samples.controllers;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.compellingcode.cloud.lambda.mvc.domain.LambdaRequest;
import com.compellingcode.cloud.lambda.mvc.endpoint.Endpoint;
import com.compellingcode.cloud.lambda.mvc.endpoint.EndpointParameter;
import com.compellingcode.cloud.lambda.mvc.endpoint.ParameterType;
import com.compellingcode.cloud.lambda.mvc.endpoint.RequestMethod;
import com.compellingcode.cloud.lambda.mvc.view.ClasspathResourceLambdaResponse;
import com.compellingcode.cloud.lambda.mvc.view.DefaultErrorResponse;
import com.compellingcode.cloud.lambda.mvc.view.HtmlLambdaResponse;
import com.compellingcode.cloud.lambda.mvc.view.JSONLambdaResponse;
import com.compellingcode.cloud.lambda.mvc.view.LambdaResponse;
import com.compellingcode.cloud.lambda.mvc.view.RedirectResponse;
import com.compellingcode.utils.parser.form.multipart.domain.FormElement;

public class MainController {
	static final Logger logger = LogManager.getLogger(MainController.class);
	
	@Endpoint(value={"/test", "/"}, method=RequestMethod.GET)
	public LambdaResponse test() {
		return new HtmlLambdaResponse("hello.tpl");
	}
	
	@Endpoint(value= {"/google"})
	public LambdaResponse google() {
		return new RedirectResponse("http://www.google.com", true);
	}
	
	@Endpoint(value= {"/error/{errorNumber}"})
	public LambdaResponse error(@EndpointParameter(type=ParameterType.PATH_PARAMETER, name="errorNumber") int error) {
		return new DefaultErrorResponse(error);
	}

	@Endpoint({"/pathtest/{path+}"})
	public LambdaResponse pathtest(@EndpointParameter(type=ParameterType.PATH_PARAMETER, name="path") String path) {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("path", path);
		return new JSONLambdaResponse(values);
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
	
	@Endpoint(value={"/postback"}, method=RequestMethod.GET)
	public LambdaResponse getPostback(LambdaRequest request) {
		return new HtmlLambdaResponse("postback.tpl");
	}
	
	@Endpoint(value={"/postback"}, method=RequestMethod.POST)
	public LambdaResponse postPostback(LambdaRequest request) {
		HtmlLambdaResponse response = new HtmlLambdaResponse("postback.tpl");
		
		try {
			Object o = request.getPostParameters().get("somefile");
			if(o instanceof FormElement) {
				FormElement el = (FormElement)o;
				if(el.isFile()) {
					byte[] data = new byte[10 * 1024 * 1024];
					InputStream is = el.getFile().openInputStream();
					int count = is.read(data);
					String base64 = new String(Base64.getEncoder().encode(Arrays.copyOfRange(data, 0, count)));
					
					response.setVariable("image", base64);
					response.setVariable("mime", el.getMimeType());
				}
			}
		} catch(Exception ex) {
			return new DefaultErrorResponse(500);
		}
		
		return response;
	}
	
}
