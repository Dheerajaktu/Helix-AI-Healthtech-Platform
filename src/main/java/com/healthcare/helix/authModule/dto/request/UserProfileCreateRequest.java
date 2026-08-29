package com.healthcare.helix.authModule.dto.request;

import java.time.LocalDate;
import java.util.UUID;

public record UserProfileCreateRequest(
        UUID userId,
        String email,
        String mobile,
        String role,
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String gender
) {
}
