package com.guvi.apigateway.controller;

import com.guvi.apigateway.dto.ApiResponse;
import com.guvi.apigateway.service.GatewayRoutingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/gateway")
public class GatewayController {

    private final GatewayRoutingService routingService;
    public GatewayController(GatewayRoutingService routingService) {
        this.routingService = routingService;
    }

    @GetMapping("/{service}")
    public ApiResponse route(@PathVariable String service,
                             HttpServletRequest request) {

        String user = (String) request.getAttribute("userEmail");
        Map<String, Object> result = routingService.route(service, user);

        return new ApiResponse(true, "Request routed successfully",
                result);
    }
}
