package com.healthcare.helix.authModule.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefreshTokenRequest {
    private String refreshToken;
}