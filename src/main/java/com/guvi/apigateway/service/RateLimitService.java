package com.guvi.apigateway.service;

import com.guvi.apigateway.model.RateLimitEntry;
import com.guvi.apigateway.repo.RateLimitRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RateLimitService {
    private final RateLimitRepository repository;

    @Value("${gateway.rate-limit.max-tokens}")
    private int maxTokens;

    @Value("${gateway.rate-limit.refill-rate}")
    private int refillRate;
    public RateLimitService(RateLimitRepository repository) {
        this.repository = repository;
    }

    public boolean allowRequest(String key) {
        RateLimitEntry entry = repository.findByKey(key).orElse(null);
        if (entry == null) {
            entry = new RateLimitEntry();
            entry.setKey(key);
            entry.setMaxTokens(maxTokens);
            entry.setTokens(maxTokens - 1);
            entry.setLastRefillTime(LocalDateTime.now());

            repository.save(entry);
            return true;
        }
        refillTokens(entry);
        if (entry.getTokens() > 0) {
            entry.setTokens(entry.getTokens() - 1);
            repository.save(entry);
            return true;
        }

        repository.save(entry);
        return false;
    }
    private void refillTokens(RateLimitEntry entry) {
        LocalDateTime now = LocalDateTime.now();
        long seconds = java.time.Duration.between(entry.getLastRefillTime(), now)
                .getSeconds();
        int tokensToAdd = (int) (seconds * refillRate);
        if (tokensToAdd > 0) {
            entry.setTokens(Math.min(entry.getTokens() + tokensToAdd, maxTokens));
            entry.setLastRefillTime(now);
        }
    }
}
