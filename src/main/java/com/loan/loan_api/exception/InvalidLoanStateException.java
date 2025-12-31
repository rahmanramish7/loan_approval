package com.loan.loan_api.exception;

public class InvalidLoanStateException extends RuntimeException {

    public InvalidLoanStateException(String message) {
        super(message);
    }
}





//Why?
//
//Used when someone breaks workflow rules
//
//Example: approving before review