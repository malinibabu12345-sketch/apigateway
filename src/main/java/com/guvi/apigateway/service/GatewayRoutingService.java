package com.guvi.apigateway.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class GatewayRoutingService {
    public Map<String, Object> route(String service, String userEmail) {
        return switch (service) {
            case "service-a" -> serviceAHello(userEmail);
            case "service-b" -> serviceBData(userEmail);
            default -> Map.of(
                    "service", "unknown",
                    "message", "Invalid service",
                    "timestamp", LocalDateTime.now().toString()); };
    }

    public Map<String, Object> serviceAHello(String userEmail) {
        return Map.of(
                "service", "service-a",
                "message", "hello from internal service A",
                "user", userEmail,
                "timestamp", LocalDateTime.now().toString());
    }

    public Map<String, Object> serviceBData(String userEmail) {
        return Map.of(
                "service", "service-b",
                "data", "sample data payload",
                "user", userEmail,
                "timestamp", LocalDateTime.now().toString());
    }
}
