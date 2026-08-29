package com.healthcare.helix.userModule.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileBasicResponse {
    private UUID userId;
    private int status;
    private String message;
}
