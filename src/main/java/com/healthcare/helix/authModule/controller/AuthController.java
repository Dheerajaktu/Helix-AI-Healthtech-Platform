package com.healthcare.helix.authModule.controller;

import com.healthcare.helix.authModule.dto.request.LoginRequest;
import com.healthcare.helix.authModule.dto.request.RefreshTokenRequest;
import com.healthcare.helix.authModule.dto.request.RegisterRequest;
import com.healthcare.helix.authModule.dto.response.LoginResponse;
import com.healthcare.helix.authModule.dto.response.RefreshTokenResponse;
import com.healthcare.helix.authModule.dto.response.RegisterResponse;
import com.healthcare.helix.authModule.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/signup")
    public ResponseEntity<RegisterResponse> registerRequest(@Valid @RequestBody RegisterRequest registerRequest) {
        RegisterResponse response = authService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public  ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh-token")
    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {
        return authService.refreshTokenFromRequest(request);
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(String email) {
        return "";
    }

    @PostMapping("/reset-password")
    public String resetPassword(String email) {
        return "";
    }


    @PostMapping("/verify-email")
    public String verifyEmail(String email) {
        return "";
    }


    @PostMapping("/send-otp")
    public String sendOtp(String email) {
        return "";
    }


    @PostMapping("/verify-otp")
    public String verifyOtp(String email) {
        return "";
    }


    @PostMapping("/logout")
    public String logout(@RequestBody RegisterRequest registerRequest) {
        return "";
    }


}
