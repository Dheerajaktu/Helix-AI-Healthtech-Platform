package com.healthcare.helix.userModule.service.impl;

import com.healthcare.helix.common.exception.ResourceNotFoundException;
import com.healthcare.helix.userModule.dto.request.MedicalConditionRequest;
import com.healthcare.helix.userModule.dto.response.MedicalConditionResponse;
import com.healthcare.helix.userModule.entity.MedicalCondition;
import com.healthcare.helix.userModule.entity.MedicalProfile;
import com.healthcare.helix.userModule.repository.MedicalConditionRepository;
import com.healthcare.helix.userModule.repository.MedicalProfileRepository;
import com.healthcare.helix.userModule.service.MedicalConditionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MedicalConditionServiceImpl implements MedicalConditionService {
    private final MedicalConditionRepository medicalConditionRepository;
    private final MedicalProfileRepository medicalProfileRepository;

    @Override
    @Transactional
    public MedicalConditionResponse addCondition(UUID userId, MedicalConditionRequest request) {
        MedicalProfile profile = medicalProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Medical profile not found for userId: " + userId));

        MedicalCondition condition = new MedicalCondition();
        condition.setConditionName(request.getConditionName());
        condition.setDescription(request.getDescription());
        condition.setDiagnosedDate(request.getDiagnosedDate());
        condition.setActive(request.isActive());

        // Bidirectional helper method use kar rahe hain (jo entity mein already hai)
        profile.addMedicalCondition(condition);

        medicalProfileRepository.save(profile); // cascade se condition bhi save ho jayega
        log.info("Medical condition added for userId: {}", userId);

        return mapToResponse(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicalConditionResponse> getMyConditions(UUID userId) {
        MedicalProfile profile = medicalProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Medical profile not found for userId: " + userId));

        return medicalConditionRepository.findByMedicalProfileId(profile.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MedicalConditionResponse getConditionById(UUID userId, UUID conditionId) {
        MedicalProfile profile = medicalProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Medical profile not found for userId: " + userId));

        MedicalCondition condition = medicalConditionRepository
                .findByIdAndMedicalProfileId(conditionId, profile.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Condition not found: " + conditionId));

        return mapToResponse(condition);
    }

    @Override
    @Transactional
    public MedicalConditionResponse updateCondition(UUID userId, UUID conditionId, MedicalConditionRequest request) {
        MedicalProfile profile = medicalProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Medical profile not found for userId: " + userId));

        MedicalCondition condition = medicalConditionRepository
                .findByIdAndMedicalProfileId(conditionId, profile.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Condition not found: " + conditionId));

        condition.setConditionName(request.getConditionName());
        condition.setDescription(request.getDescription());
        condition.setDiagnosedDate(request.getDiagnosedDate());
        condition.setActive(request.isActive());

        MedicalCondition updated = medicalConditionRepository.save(condition);
        log.info("Medical condition updated: {}", conditionId);

        return mapToResponse(updated);
    }

    @Override
    @Transactional
    public void deleteCondition(UUID userId, UUID conditionId) {
        MedicalProfile profile = medicalProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Medical profile not found for userId: " + userId));

        MedicalCondition condition = medicalConditionRepository
                .findByIdAndMedicalProfileId(conditionId, profile.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Condition not found: " + conditionId));

        profile.removeMedicalCondition(condition); // bidirectional helper, orphanRemoval se DB se bhi delete hoga
        medicalProfileRepository.save(profile);

        log.info("Medical condition deleted: {}", conditionId);
    }

    private MedicalConditionResponse mapToResponse(MedicalCondition c) {
        return MedicalConditionResponse.builder()
                .id(c.getId())
                .conditionName(c.getConditionName())
                .description(c.getDescription())
                .diagnosedDate(c.getDiagnosedDate())
                .active(c.isActive())
                .build();
    }

}
