package com.loan.loan_api.exception;
public class LoanNotFoundException extends RuntimeException {
		public LoanNotFoundException(String message) {
		super(message);
	}
}


//Replaces vague RuntimeException
//
//Clearly tells what failed