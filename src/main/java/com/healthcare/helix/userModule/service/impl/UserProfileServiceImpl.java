package com.healthcare.helix.userModule.service.impl;

import com.healthcare.helix.userModule.dto.request.UpdateUserProfileRequest;
import com.healthcare.helix.userModule.dto.response.UserProfileResponse;
import com.healthcare.helix.userModule.entity.UserProfile;
import com.healthcare.helix.userModule.exception.UserNotFoundException;
import com.healthcare.helix.userModule.repository.UserProfileRepository;
import com.healthcare.helix.userModule.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j

public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;


    public UserProfileResponse getMyProfile(UUID userId){
        UserProfile profile = userProfileRepository.findByUserId(userId).orElseThrow(() ->
                new UserNotFoundException("User not found userId: " + userId));
        return mapToResponse(profile);
    }


    public UserProfileResponse updateMyProfile(UUID userId, UpdateUserProfileRequest request){
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException("Profile not found for userId: " + userId));
        // Updating only not null fields (partial update support)
        if (request.getFirstName() != null) profile.setFirstName(request.getFirstName());
        if (request.getLastName() != null) profile.setLastName(request.getLastName());
        if (request.getAddress() != null) profile.setAddress(request.getAddress());
        if (request.getCity() != null) profile.setCity(request.getCity());
        if (request.getState() != null) profile.setState(request.getState());
        if (request.getCountry() != null) profile.setCountry(request.getCountry());

        profile.setUserBasicProfileCompleted(true);

        UserProfile updated = userProfileRepository.save(profile);
        log.info("Profile updated for userId: {}", userId);

        return mapToResponse(updated);
    }

    @Transactional(readOnly = true)
    public UserProfileResponse getProfileByUserId(UUID userId){
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException("Profile not found for userId: " + userId));

        return mapToResponse(profile);
    }

    @Transactional(readOnly = true)
    public List<UserProfileResponse> getAllUsers(){

        List<UserProfile> profiles = userProfileRepository.findAllWithMedicalProfile();

        return profiles
                .stream()
                .map(this:: mapToResponse)
                .collect(Collectors.toList());

    }


    private UserProfileResponse mapToResponse(UserProfile profile) {
        return UserProfileResponse.builder()
                .userId(profile.getUserId())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .email(profile.getEmail())
                .mobile(profile.getMobile())
                .dateOfBirth(profile.getDateOfBirth())
                .gender(profile.getGender())
                .address(profile.getAddress())
                .city(profile.getCity())
                .state(profile.getState())
                .country(profile.getCountry())
                .emailVerified(profile.isEmailVerified())
                .userProfileCompleted(profile.isUserProfileCompleted())
                .userBasicProfileCompleted(profile.isUserBasicProfileCompleted())
                .build();
    }


}
