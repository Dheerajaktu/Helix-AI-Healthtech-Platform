package com.healthcare.helix.userModule.controller;

import com.healthcare.helix.userModule.dto.request.MedicalProfileRequest;
import com.healthcare.helix.userModule.dto.response.MedicalProfileResponse;
import com.healthcare.helix.userModule.service.MedicalProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/medical-profile")
@RequiredArgsConstructor
public class MedicalProfileController {

    private final MedicalProfileService medicalProfileService;

    @GetMapping
    public ResponseEntity<MedicalProfileResponse> getMyMedicalProfile(Authentication authentication) {
        UUID userId = (UUID) authentication.getPrincipal();
        return ResponseEntity.ok(medicalProfileService.getMyMedicalProfile(userId));
    }

    @PutMapping
    public ResponseEntity<MedicalProfileResponse> updateMyMedicalProfile(
            Authentication authentication,
            @Valid @RequestBody MedicalProfileRequest request) {

        UUID userId = (UUID) authentication.getPrincipal();
        return ResponseEntity.ok(medicalProfileService.updateMyMedicalProfile(userId, request));
    }
}
