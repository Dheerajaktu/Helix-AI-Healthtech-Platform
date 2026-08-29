package com.healthcare.helix.userModule.service;


import com.healthcare.helix.userModule.dto.request.UpdateUserProfileRequest;
import com.healthcare.helix.userModule.dto.response.UserProfileResponse;

import java.util.List;
import java.util.UUID;

public interface UserProfileService {

    UserProfileResponse getMyProfile(UUID userId);

    UserProfileResponse updateMyProfile(UUID userId, UpdateUserProfileRequest request);

    UserProfileResponse getProfileByUserId(UUID userId);

    List<UserProfileResponse> getAllUsers();

}
