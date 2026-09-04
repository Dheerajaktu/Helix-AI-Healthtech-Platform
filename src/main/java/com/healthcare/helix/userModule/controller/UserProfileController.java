package com.healthcare.helix.userModule.controller;


import com.healthcare.helix.userModule.dto.request.UpdateUserProfileRequest;
import com.healthcare.helix.userModule.dto.response.UserProfileFullResponse;
import com.healthcare.helix.userModule.dto.response.UserProfileResponse;
import com.healthcare.helix.userModule.service.UserProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    // Fetch own Profile
    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getMyProfile(Authentication authentication) {
        UUID userId = (UUID) authentication.getPrincipal();
        UserProfileResponse response = userProfileService.getMyProfile(userId);
        return ResponseEntity.ok(response);
    }

    // Update User Profile(Own profile)
    @PutMapping("/profile")
    public ResponseEntity<UserProfileResponse> updateMyProfile(
            Authentication authentication,
            @Valid @RequestBody UpdateUserProfileRequest request) {

        UUID userId = (UUID) authentication.getPrincipal();
        UserProfileResponse response = userProfileService.updateMyProfile(userId, request);
        return ResponseEntity.ok(response);
    }

    // Fetching specific User details based on userid
    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserProfileResponse> getProfileByUserId(@PathVariable UUID userId) {
        UserProfileResponse response = userProfileService.getProfileByUserId(userId);
        return ResponseEntity.ok(response);
    }

    // Fetching All users and associated data in list — N+1 handling
    @GetMapping
    public ResponseEntity<List<UserProfileResponse>> getAllUsers() {
        List<UserProfileResponse> users = userProfileService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/profile/full")
    public ResponseEntity<UserProfileFullResponse> getMyFullProfile(Authentication authentication) {
        UUID userId = (UUID) authentication.getPrincipal();
        return ResponseEntity.ok(userProfileService.getFullProfile(userId));
    }

}
