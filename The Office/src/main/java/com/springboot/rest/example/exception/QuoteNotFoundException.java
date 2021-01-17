package com.springboot.rest.example.exception;

public class QuoteNotFoundException extends RuntimeException {

	public QuoteNotFoundException(String exception) {
		super(exception);
	}

}