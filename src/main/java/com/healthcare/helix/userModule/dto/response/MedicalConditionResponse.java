package com.healthcare.helix.userModule.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalConditionResponse {
    private UUID id;
    private String conditionName;
    private String description;
    private LocalDate diagnosedDate;
    private boolean active;
}