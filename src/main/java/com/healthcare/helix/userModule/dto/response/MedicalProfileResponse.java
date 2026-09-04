package com.healthcare.helix.userModule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalProfileResponse {
    private UUID id;
    private String bloodGroup;
    private Double height;
    private Double weight;
    private String allergies;
    private String currentMedications;
    private String medicalHistory;
    private List<MedicalConditionResponse> medicalConditions;
    private List<FamilyMedicalHistoryResponse> familyMedicalHistory;
}