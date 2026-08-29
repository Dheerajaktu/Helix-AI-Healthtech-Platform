package com.healthcare.helix.userModule.service.impl;

import com.healthcare.helix.common.exception.UserAlreadyExistsException;
import com.healthcare.helix.userModule.dto.request.UserProfileBasicRequest;
import com.healthcare.helix.userModule.dto.response.UserProfileBasicResponse;
import com.healthcare.helix.userModule.entity.MedicalProfile;
import com.healthcare.helix.userModule.entity.UserProfile;
import com.healthcare.helix.userModule.repository.UserProfileRepository;
import com.healthcare.helix.userModule.service.InternalUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class InternalUserServiceImpl implements InternalUserService {

    private final UserProfileRepository userProfileRepository;

    @Override
    @Transactional
    public UserProfileBasicResponse createBasicProfile(UserProfileBasicRequest request) {

        if (userProfileRepository.existsByUserId(request.getUserId())) {
            log.warn("Profile already exists for userId: {}", request.getUserId());
            throw new UserAlreadyExistsException(
                    "User profile already exists for userId: " + request.getUserId());
        }

        UserProfile userProfile = UserProfile.builder()
                .userId(request.getUserId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .mobile(request.getMobile())
                .dateOfBirth(request.getDateOfBirth())
                .gender(request.getGender())
                .build();

        MedicalProfile medicalProfile = new MedicalProfile();
        userProfile.setMedicalProfile(medicalProfile); // bidirectional helper method

        UserProfile savedProfile = userProfileRepository.save(userProfile);

        log.info("Basic profile created successfully for userId: {}", savedProfile.getUserId());

        return UserProfileBasicResponse.builder()
                .userId(savedProfile.getUserId())
                .status(HttpStatus.CREATED.value())
                .message("User profile created successfully.")
                .build();
    }

}
