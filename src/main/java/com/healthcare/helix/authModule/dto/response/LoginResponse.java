package com.healthcare.helix.authModule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String accessToken;

    private String refreshToken;

    private String tokenType;

    private Long expiresIn;

    private String role;

    private UUID userId;

}
