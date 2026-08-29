package com.healthcare.helix.authModule.service;


import com.healthcare.helix.authModule.dto.request.LoginRequest;
import com.healthcare.helix.authModule.dto.request.RefreshTokenRequest;
import com.healthcare.helix.authModule.dto.request.RegisterRequest;
import com.healthcare.helix.authModule.dto.response.LoginResponse;
import com.healthcare.helix.authModule.dto.response.RefreshTokenResponse;
import com.healthcare.helix.authModule.dto.response.RegisterResponse;

public interface AuthService {

    public RegisterResponse register(RegisterRequest registerRequest);

    public LoginResponse login(LoginRequest loginRequest);

    public RefreshTokenResponse refreshTokenFromRequest(RefreshTokenRequest request);

}
