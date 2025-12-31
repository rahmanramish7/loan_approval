package com.loan.loan_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LoanNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleLoanNotFound(
            LoanNotFoundException ex) {

        ApiErrorResponse error = new ApiErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Loan Not Found",
                ex.getMessage()
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidLoanStateException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidLoanState(
            InvalidLoanStateException ex) {

        ApiErrorResponse error = new ApiErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Invalid Loan State",
                ex.getMessage()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedActionException.class)
    public ResponseEntity<ApiErrorResponse> handleUnauthorizedAction(
            UnauthorizedActionException ex) {

        ApiErrorResponse error = new ApiErrorResponse(
                HttpStatus.FORBIDDEN.value(),
                "Unauthorized Action",
                ex.getMessage()
        );

        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    // Fallback for any unhandled exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(
            Exception ex) {

        ApiErrorResponse error = new ApiErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "Something went wrong"
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

		
		
		
		
		
//		
//		What it does
//
//		Catches all exceptions thrown anywhere in the application.
//
//		How it works
//
//		@ControllerAdvice intercepts exceptions
//Marks this class as a global interceptor for controllers

//Applies to ALL controllers automatically
//
//		@ExceptionHandler maps them to HTTP responses,Each method inside this //class handles one type of exception.
//Spring picks the most specific match.
//
//		Responsibilities
//
//		Converts Java exceptions → HTTP responses
//
//		Prevents stack trace leakage
//
//		Keeps controllers clean
		
		
//		
//		This file centrally catches all exceptions thrown by controllers/services and converts them into clean HTTP responses.
//		
//		
		
//		“GlobalExceptionHandler uses @ControllerAdvice and @ExceptionHandler to intercept all application exceptions and convert them into consistent, meaningful HTTP responses.”









//
//
//Why ResponseEntity?
//
//Because HTTP responses have two parts:
//
//Body
//
//Status code
//
//ResponseEntity lets you control both.
//
//Why ApiErrorResponse?
//
//Because returning raw strings like "Loan not found" is amateur-level.