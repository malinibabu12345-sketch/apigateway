package com.guvi.apigateway.controller;

import com.guvi.apigateway.dto.ApiResponse;
import com.guvi.apigateway.model.ApiLog;
import com.guvi.apigateway.repo.ApiLogRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/monitor")
public class MonitoringController {

    private final ApiLogRepository repository;

    public MonitoringController(ApiLogRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/logs/user")
    public ApiResponse getLogsByUser(@RequestParam String email) {

        List<ApiLog> logs = repository.findByUserEmailOrderByTimestampDesc(email);

        return new ApiResponse(true, "Logs fetched", logs);
    }

    @GetMapping("/logs/recent")
    public ApiResponse getRecentLogs(@RequestParam int minutes) {

        LocalDateTime time = LocalDateTime.now().minusMinutes(minutes);

        List<ApiLog> logs = repository.findByTimestampAfterOrderByTimestampDesc(time);

        return new ApiResponse(true, "Recent logs fetched", logs);
    }

    @GetMapping("/logs/violations")
    public ApiResponse getViolations() {

        List<ApiLog> logs = repository.
                findByRateLimitViolationTrueOrderByTimestampDesc();

        return new ApiResponse(true, "Rate limit violations", logs);
    }
}
