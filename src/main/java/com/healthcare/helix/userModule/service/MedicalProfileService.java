package com.healthcare.helix.userModule.service;

import com.healthcare.helix.userModule.dto.request.MedicalProfileRequest;
import com.healthcare.helix.userModule.dto.response.MedicalProfileResponse;

import java.util.UUID;

public interface MedicalProfileService {
    MedicalProfileResponse getMyMedicalProfile(UUID userId);

    MedicalProfileResponse updateMyMedicalProfile(UUID userId, MedicalProfileRequest request);
}
