package com.loan.loan_api.exception;


public class UnauthorizedActionException extends RuntimeException {
	
	public UnauthorizedActionException(String message) {
	super(message);

	
}
}


//
//Why?
//
//Will be used with roles later
//
//Cleaner than returning 403 manually