package com.guvi.apigateway.repo;

import com.guvi.apigateway.model.RateLimitEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RateLimitRepository extends MongoRepository
        <RateLimitEntry, String> {
    Optional<RateLimitEntry> findByKey(String key);

}
