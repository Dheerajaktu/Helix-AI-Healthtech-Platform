package com.healthcare.helix.userModule.dto.request;


import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserProfileRequest {
    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    private String address;
    private String city;
    private String state;
    private String country;
}
