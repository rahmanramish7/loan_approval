package com.loan.loan_api.controller;

import com.loan.loan_api.entity.User;
import com.loan.loan_api.enums.Role;
import com.loan.loan_api.repository.UserRepository;
import com.loan.loan_api.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepo;

    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    
    
    
    

    // ================= REGISTER =================

    @PostMapping("/register")
    public String register(@RequestBody User user) {

        // basic validation
        if (userRepo.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // default role for safety
        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }

        userRepo.save(user);

        return "User registered successfully";
    }
    
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User request) {

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepo.findByUsername(request.getUsername()).get();

        String token = jwtUtil.generateToken(
                user.getUsername(),
                user.getRole().name()
        );

        return Map.of("token", token);
    }
}
