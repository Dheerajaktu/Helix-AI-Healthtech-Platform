package com.healthcare.helix.userModule.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalProfileRequest {
    private String bloodGroup;
    private Double height;
    private Double weight;
    private String allergies;
    private String currentMedications;
    private String medicalHistory;
}
