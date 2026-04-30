package com.guvi.apigateway.filter;

import com.guvi.apigateway.service.LoggingService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(3)
public class LoggingFilter extends OncePerRequestFilter {

    private final LoggingService loggingService;
    public LoggingFilter(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse
                                            response, FilterChain filterChain)
            throws ServletException, IOException {
        long start = System.currentTimeMillis();
        filterChain.doFilter(request, response);

        long duration = System.currentTimeMillis() - start;
        String user = (String) request.getAttribute("userEmail");
        if (user == null) user = "anonymous";

        loggingService.log(
                user,
                request.getRemoteAddr(),
                request.getRequestURI(),
                request.getMethod(),
                response.getStatus(),
                duration,
                response.getStatus() == 429);
    }
}
