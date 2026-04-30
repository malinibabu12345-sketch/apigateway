package com.guvi.apigateway.controller;

import com.guvi.apigateway.dto.ApiResponse;
import com.guvi.apigateway.dto.AuthResponse;
import com.guvi.apigateway.dto.LoginRequest;
import com.guvi.apigateway.dto.RegisterRequest;
import com.guvi.apigateway.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ApiResponse register(@Valid @RequestBody RegisterRequest request) {
        String message = authService.register(request);
        return new ApiResponse(true, message, null);
    }

    @PostMapping("/login")
    public ApiResponse login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return new ApiResponse(true, "Login successful", response);
    }
}
