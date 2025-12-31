package com.loan.loan_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}









//
//
//
//What happens here (step by step)
//
//Spring runs this method at application startup
//
//It creates one object of BCryptPasswordEncoder
//
//It stores that object in the Spring container as a bean
//
//Anywhere you write:
//
//@Autowired
//private PasswordEncoder passwordEncoder;
//PasswordEncoder is an interface used by Spring Security to:

//Hash passwords before saving
//
//👉 Spring injects this exact BCrypt encoder