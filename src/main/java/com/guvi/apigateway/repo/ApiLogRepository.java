package com.guvi.apigateway.repo;

import com.guvi.apigateway.model.ApiLog;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ApiLogRepository extends MongoRepository<ApiLog, String> {
    List<ApiLog> findByUserEmailOrderByTimestampDesc(String userEmail);

    List<ApiLog> findByTimestampAfterOrderByTimestampDesc(LocalDateTime after);

    List<ApiLog> findByRateLimitViolationTrueOrderByTimestampDesc();
}
