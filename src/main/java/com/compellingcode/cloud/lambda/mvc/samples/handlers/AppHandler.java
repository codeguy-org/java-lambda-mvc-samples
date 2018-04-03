package com.compellingcode.cloud.lambda.mvc.samples.handlers;

import com.compellingcode.cloud.lambda.mvc.exception.EndpointConflictException;
import com.compellingcode.cloud.lambda.mvc.handler.StreamHandler;
import com.compellingcode.cloud.lambda.mvc.samples.controllers.MainController;

public class AppHandler extends StreamHandler {
	
	private MainController mainController;
	
	@Override
	protected void configure() throws EndpointConflictException {
		mainController = new MainController();
		
		try {
			// TODO Auto-generated method stub
			addController(mainController);
		} catch(Exception ex) {
			throw new EndpointConflictException(ex);
		}
	}

}
