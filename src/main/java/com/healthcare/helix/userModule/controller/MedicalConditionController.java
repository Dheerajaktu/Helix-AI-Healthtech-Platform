package com.healthcare.helix.userModule.controller;

import com.healthcare.helix.userModule.dto.request.MedicalConditionRequest;
import com.healthcare.helix.userModule.dto.response.MedicalConditionResponse;
import com.healthcare.helix.userModule.service.MedicalConditionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/medical-conditions")
@RequiredArgsConstructor
public class MedicalConditionController {

    private final MedicalConditionService medicalConditionService;

    @PostMapping
    public ResponseEntity<MedicalConditionResponse> addCondition(
            Authentication authentication,
            @Valid @RequestBody MedicalConditionRequest request) {

        UUID userId = (UUID) authentication.getPrincipal();
        MedicalConditionResponse response = medicalConditionService.addCondition(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<MedicalConditionResponse>> getMyConditions(Authentication authentication) {
        UUID userId = (UUID) authentication.getPrincipal();
        return ResponseEntity.ok(medicalConditionService.getMyConditions(userId));
    }

    @GetMapping("/{conditionId}")
    public ResponseEntity<MedicalConditionResponse> getConditionById(
            Authentication authentication,
            @PathVariable UUID conditionId) {

        UUID userId = (UUID) authentication.getPrincipal();
        return ResponseEntity.ok(medicalConditionService.getConditionById(userId, conditionId));
    }

    @PutMapping("/{conditionId}")
    public ResponseEntity<MedicalConditionResponse> updateCondition(
            Authentication authentication,
            @PathVariable UUID conditionId,
            @Valid @RequestBody MedicalConditionRequest request) {

        UUID userId = (UUID) authentication.getPrincipal();
        return ResponseEntity.ok(medicalConditionService.updateCondition(userId, conditionId, request));
    }

    @DeleteMapping("/{conditionId}")
    public ResponseEntity<Void> deleteCondition(
            Authentication authentication,
            @PathVariable UUID conditionId) {

        UUID userId = (UUID) authentication.getPrincipal();
        medicalConditionService.deleteCondition(userId, conditionId);
        return ResponseEntity.noContent().build();
    }

}
