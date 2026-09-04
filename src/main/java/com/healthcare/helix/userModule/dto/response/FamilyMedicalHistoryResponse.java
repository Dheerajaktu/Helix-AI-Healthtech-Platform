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
public class FamilyMedicalHistoryResponse {
    private UUID id;
    private String relation;
    private String conditionName;
}