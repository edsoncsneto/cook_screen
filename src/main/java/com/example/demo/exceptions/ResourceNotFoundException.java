package com.example.demo.exceptions;

public class ResourceNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundException(Object identifier) {
		super("Resource not found. Identifier " + identifier);
	}
	
}
