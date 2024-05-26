package com.example.demo.services.exceptions;

public class BusinessRuleException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	/** 
	 * @param Expects a string that describes the disobeyed business rule
	 */
	
	public BusinessRuleException(String procedure) {
		super("Unable to execute procedure: " + procedure);
	}
	
}
