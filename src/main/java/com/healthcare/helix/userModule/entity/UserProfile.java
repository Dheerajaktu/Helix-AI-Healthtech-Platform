package com.healthcare.helix.userModule.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // ID of the user from Auth Service
    @Column(nullable = false, unique = true)
    private UUID userId; // from Auth-service

    @Column(nullable = false)
    private String firstName; // from Auth-service

    private String lastName;// from Auth-service (optional)

    private LocalDate dateOfBirth; // from Auth-service

//    @Enumerated(EnumType.STRING)
//    private Gender gender; // from Auth-service
    @Column(nullable = false)
    private String gender;

    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid mobile number")
    @Column(nullable = false, unique = true)
    private String mobile; // from Auth-service

    @Column(nullable = false, unique = true)
    @Email
    private String email; // from Auth-service

    @Column(nullable = false)
    private boolean emailVerified;

    @Column(nullable = false)
    private boolean isUserProfileCompleted;

    @Column(nullable = false)
    private boolean isUserBasicProfileCompleted;

    private String address;
    private String city;
    private String state;
    private String country;

    @OneToOne(mappedBy = "userProfile", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private MedicalProfile medicalProfile;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
//        country = Locale.getDefault().getCountry(); // Dynamic Name
        emailVerified = false;
        isUserProfileCompleted = false;
        isUserBasicProfileCompleted = true;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

//    @Transient  // ignoring dateOfBirth Column in table
//    public Integer getAge() {
//        if (dateOfBirth == null) return null;
//        return Period.between(dateOfBirth, LocalDate.now()).getYears();
//    }

    // ---- Helper method for bidirectional consistency ----
    public void setMedicalProfile(MedicalProfile medicalProfile) {
        if (medicalProfile == null) {
            if (this.medicalProfile != null) {
                this.medicalProfile.setUserProfileInternal(null);
            }
        } else {
            medicalProfile.setUserProfileInternal(this);
        }
        this.medicalProfile = medicalProfile;
    }

}
