package com.guvi.apigateway.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "rate_limit_entries")
public class RateLimitEntry {

    @Id
    private String id;

    @Indexed
    private String key;
    private int tokens;
    private int maxTokens;
    private LocalDateTime lastRefillTime;

    public RateLimitEntry() {}
    public RateLimitEntry(String key, int tokens, int maxTokens,
                          LocalDateTime lastRefillTime) {
        this.key = key;
        this.tokens = tokens;
        this.maxTokens = maxTokens;
        this.lastRefillTime = lastRefillTime;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }

    public int getTokens() { return tokens; }
    public void setTokens(int tokens) { this.tokens = tokens; }

    public int getMaxTokens() { return maxTokens; }
    public void setMaxTokens(int maxTokens) { this.maxTokens = maxTokens; }

    public LocalDateTime getLastRefillTime() { return lastRefillTime; }
    public void setLastRefillTime(LocalDateTime lastRefillTime) {
        this.lastRefillTime = lastRefillTime; }
}
