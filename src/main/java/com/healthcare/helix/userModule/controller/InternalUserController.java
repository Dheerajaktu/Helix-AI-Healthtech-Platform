package com.healthcare.helix.userModule.controller;

import com.healthcare.helix.userModule.dto.request.UserProfileBasicRequest;
import com.healthcare.helix.userModule.dto.response.UserProfileBasicResponse;
import com.healthcare.helix.userModule.service.InternalUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/api/v1/user")
@RequiredArgsConstructor
public class InternalUserController {

    private final InternalUserService internalUserService;

    @PostMapping("/create-basic")
    public ResponseEntity<UserProfileBasicResponse> createBasicProfile(
            @Valid @RequestBody UserProfileBasicRequest request) {

        UserProfileBasicResponse response = internalUserService.createBasicProfile(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
