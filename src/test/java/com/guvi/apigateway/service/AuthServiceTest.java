package com.guvi.apigateway.service;

import com.guvi.apigateway.dto.LoginRequest;
import com.guvi.apigateway.dto.RegisterRequest;
import com.guvi.apigateway.model.User;
import com.guvi.apigateway.repo.UserRepository;
import com.guvi.apigateway.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @Test
    void testRegisterSuccess() {
        RegisterRequest request = new RegisterRequest();
        request.setName("Test");
        request.setEmail("test@gmail.com");
        request.setPassword("1234");
        when(userRepository.existsByEmail("test@gmail.com")).thenReturn(false);
        when(passwordEncoder.encode("1234")).thenReturn("encoded");

        String result = authService.register(request);
        assertEquals("User registered successfully", result);
        verify(userRepository).save(any(User.class));
    }
    @Test
    void testLoginSuccess() {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@gmail.com");
        request.setPassword("1234");

        User user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("encoded");
        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(user));
        when(passwordEncoder.matches("1234", "encoded")).thenReturn(true);
        when(jwtUtil.generateToken("test@gmail.com")).thenReturn("token123");

        var response = authService.login(request);
        assertEquals("token123", response.getToken());
        assertEquals("test@gmail.com", response.getEmail());
    }
}

