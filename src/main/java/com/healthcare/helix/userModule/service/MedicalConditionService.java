package com.healthcare.helix.userModule.service;

import com.healthcare.helix.userModule.dto.request.MedicalConditionRequest;
import com.healthcare.helix.userModule.dto.response.MedicalConditionResponse;

import java.util.List;
import java.util.UUID;

public interface MedicalConditionService {
    MedicalConditionResponse addCondition(UUID userId, MedicalConditionRequest request);

    List<MedicalConditionResponse> getMyConditions(UUID userId);

    MedicalConditionResponse getConditionById(UUID userId, UUID conditionId);

    MedicalConditionResponse updateCondition(UUID userId, UUID conditionId, MedicalConditionRequest request);

    void deleteCondition(UUID userId, UUID conditionId);
}
