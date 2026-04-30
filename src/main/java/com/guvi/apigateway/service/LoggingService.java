package com.guvi.apigateway.service;

import com.guvi.apigateway.model.ApiLog;
import com.guvi.apigateway.repo.ApiLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LoggingService {
    private final ApiLogRepository repository;

    public LoggingService(ApiLogRepository repository) {
        this.repository = repository;
    }

    public void log(String userEmail,
                    String ip, String endpoint,
                    String method,
                    int status, long time,
                    boolean rateLimitViolation) {
        ApiLog log = new ApiLog();
        log.setUserEmail(userEmail);
        log.setIpAddress(ip);
        log.setEndpoint(endpoint);
        log.setHttpMethod(method);
        log.setResponseStatus(status);
        log.setResponseTimeMs(time);
        log.setRateLimitViolation(rateLimitViolation);
        log.setTimestamp(LocalDateTime.now());

        repository.save(log);
    }
}
