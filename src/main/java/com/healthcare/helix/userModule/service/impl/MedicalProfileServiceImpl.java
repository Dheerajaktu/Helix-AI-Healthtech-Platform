package com.healthcare.helix.userModule.service.impl;


import com.healthcare.helix.common.exception.ResourceNotFoundException;
import com.healthcare.helix.userModule.dto.request.MedicalProfileRequest;
import com.healthcare.helix.userModule.dto.response.MedicalConditionResponse;
import com.healthcare.helix.userModule.dto.response.MedicalProfileResponse;
import com.healthcare.helix.userModule.entity.MedicalProfile;
import com.healthcare.helix.userModule.repository.MedicalConditionRepository;
import com.healthcare.helix.userModule.repository.MedicalProfileRepository;
import com.healthcare.helix.userModule.service.MedicalProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MedicalProfileServiceImpl implements MedicalProfileService {
    private final MedicalProfileRepository medicalProfileRepository;
    private final MedicalConditionRepository medicalConditionRepository;

    @Override
    @Transactional(readOnly = true)
    public MedicalProfileResponse getMyMedicalProfile(UUID userId) {
        MedicalProfile profile = medicalProfileRepository.findByUserId(userId).orElseThrow(() ->
                new ResourceNotFoundException("Medical profile not found for userId: " + userId));
        return mapToResponse(profile);
    }

    @Override
    @Transactional
    public MedicalProfileResponse updateMyMedicalProfile(UUID userId, MedicalProfileRequest request) {
        MedicalProfile profile = medicalProfileRepository.findByUserId(userId).orElseThrow(() ->
                new ResourceNotFoundException("Medical profile not found for userId: " + userId));

        if (request.getBloodGroup() != null) profile.setBloodGroup(request.getBloodGroup());
        if (request.getHeight() != null) profile.setHeight(request.getHeight());
        if (request.getWeight() != null) profile.setWeight(request.getWeight());
        if (request.getAllergies() != null) profile.setAllergies(request.getAllergies());
        if (request.getCurrentMedications() != null) profile.setCurrentMedications(request.getCurrentMedications());
        if (request.getMedicalHistory() != null) profile.setMedicalHistory(request.getMedicalHistory());

        MedicalProfile updated = medicalProfileRepository.save(profile);
        log.info("Medical profile updated for userId: {}", userId);

        return mapToResponse(updated);
    }

    private MedicalProfileResponse mapToResponse(MedicalProfile profile) {
        List<MedicalConditionResponse> conditions = medicalConditionRepository.findByMedicalProfileId(profile.getId())
                .stream().map(this::mapConditionToResponse).toList();

        return MedicalProfileResponse.builder().id(profile.getId()).bloodGroup(profile
                .getBloodGroup())
                .height(profile.getHeight())
                .weight(profile.getWeight())
                .allergies(profile.getAllergies())
                .currentMedications(profile.getCurrentMedications())
                .medicalHistory(profile.getMedicalHistory())
                .medicalConditions(conditions).build();
    }

    private MedicalConditionResponse mapConditionToResponse(com.healthcare.helix.userModule.entity.MedicalCondition c) {
        return MedicalConditionResponse.builder()
                .id(c.getId())
                .conditionName(c.getConditionName())
                .description(c.getDescription())
                .diagnosedDate(c.getDiagnosedDate())
                .active(c.isActive())
                .build();
    }
}
