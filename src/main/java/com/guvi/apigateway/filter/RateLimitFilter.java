package com.guvi.apigateway.filter;

import com.guvi.apigateway.service.LoggingService;
import com.guvi.apigateway.service.RateLimitService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(2)
public class RateLimitFilter extends OncePerRequestFilter {
    private final RateLimitService rateLimitService;
    private final LoggingService loggingService;

    public RateLimitFilter(RateLimitService rateLimitService,
                           LoggingService loggingService) {
        this.rateLimitService = rateLimitService;
        this.loggingService = loggingService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        if (path.contains("/swagger-ui")
                || path.contains("/v3/api-docs")
                || path.contains("/swagger-resources")
                || path.contains("/webjars")
                || path.contains("/api/monitor")) {

            filterChain.doFilter(request, response);
            return;
        }

        String user = (String) request.getAttribute("userEmail");
        if (user == null) {
            user = request.getRemoteAddr();
        }
        boolean allowed = rateLimitService.allowRequest(user);

        if (!allowed) {
            String userEmail = (String) request.getAttribute("userEmail");
            if (userEmail == null) userEmail = "anonymous";
            loggingService.log(
                    userEmail,
                    request.getRemoteAddr(),
                    request.getRequestURI(),
                    request.getMethod(),
                    429, 0,
                    true);
            response.setStatus(429);
            response.setContentType("application/json");
            response.getWriter().write(
                    "{\"success\":false,\"message\":\"Too Many " +
                            "Requests - Rate limit exceeded\"}");
            return;
        }
        filterChain.doFilter(request, response);
    }
}

