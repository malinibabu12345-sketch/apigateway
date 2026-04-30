package com.guvi.apigateway.service;

import com.guvi.apigateway.model.RateLimitEntry;
import com.guvi.apigateway.repo.RateLimitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RateLimitServiceTest {

    @Mock
    private RateLimitRepository repository;
    @InjectMocks
    private RateLimitService service;
    @Test
    void testAllowRequest_whenTokensAvailable() {

        RateLimitEntry entry = new RateLimitEntry(
                "user", 5, 10, LocalDateTime.now());
        when(repository.findByKey("user")).thenReturn(Optional.of(entry));

        boolean result = service.allowRequest("user");
        assertTrue(result);
        verify(repository).save(any(RateLimitEntry.class));
    }

    @Test
    void testBlockRequest_whenNoTokens() {
        RateLimitEntry entry = new RateLimitEntry(
                "user", 0, 10, LocalDateTime.now());
        when(repository.findByKey("user")).thenReturn(Optional.of(entry));

        boolean result = service.allowRequest("user");
        assertFalse(result);
        verify(repository).save(any(RateLimitEntry.class));
    }
}