package com.healthcare.helix.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {


    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException ) throws IOException {

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401

        Map<String, Object> body = Map.of(
                "status", 401,
                "error", "Unauthorized",
                "message", "Invalid, missing, or expired token"
        );

        new ObjectMapper().writeValue(response.getOutputStream(), body);
    }


}
