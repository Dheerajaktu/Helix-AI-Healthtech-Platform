package com.healthcare.helix.userModule.service;


import com.healthcare.helix.userModule.dto.request.UpdateUserProfileRequest;
import com.healthcare.helix.userModule.dto.response.UserProfileFullResponse;
import com.healthcare.helix.userModule.dto.response.UserProfileResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface UserProfileService {

    UserProfileResponse getMyProfile(UUID userId);

    UserProfileResponse updateMyProfile(UUID userId, UpdateUserProfileRequest request);

    UserProfileResponse getProfileByUserId(UUID userId);

    List<UserProfileResponse> getAllUsers();

    void createBasicProfile(
            UUID userId,
            String email,
            String mobile,
            String role,
            String firstName,
            String lastName,
            LocalDate dateOfBirth,
            String gender
    );

    UserProfileFullResponse getFullProfile(UUID userId);

   // UserProfileResponse findFullProfileByUserId(UUID userId);


}
