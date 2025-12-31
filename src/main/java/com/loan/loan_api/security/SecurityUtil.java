package com.loan.loan_api.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


public class SecurityUtil {
	
    public static String getCurrentUsername() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
if(auth ==null || !auth.isAuthenticated()) {
	throw new RuntimeException("No authenticated user found");
}
		return auth.getName();
	}
}



//
//Centralized access
//
//Reusable
//
//Clean service code