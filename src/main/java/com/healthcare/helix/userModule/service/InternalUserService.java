package com.healthcare.helix.userModule.service;

import com.healthcare.helix.userModule.dto.request.UserProfileBasicRequest;
import com.healthcare.helix.userModule.dto.response.UserProfileBasicResponse;

public interface InternalUserService {

    UserProfileBasicResponse createBasicProfile(UserProfileBasicRequest request);


}
