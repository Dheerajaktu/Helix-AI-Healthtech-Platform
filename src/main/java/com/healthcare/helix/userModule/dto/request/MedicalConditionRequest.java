package com.healthcare.helix.userModule.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MedicalConditionRequest {
    @NotBlank(message = "Condition name is required")
    private String conditionName;

    private String description;

    @PastOrPresent(message = "Diagnosed date cannot be in the future")
    private LocalDate diagnosedDate;

    private boolean active = true;
}
