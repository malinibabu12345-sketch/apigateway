package com.guvi.apigateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "gateway.rate-limit")

public class GatewayProperties {

    private int maxTokens;
    private int refillRate;

    public int getMaxTokens() {
        return maxTokens;
    }
    public void setMaxTokens(int maxTokens) {
        this.maxTokens = maxTokens;
    }

    public int getRefillRate() {
        return refillRate;
    }
    public void setRefillRate(int refillRate) {
        this.refillRate = refillRate;
    }
}
