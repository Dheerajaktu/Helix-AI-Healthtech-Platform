package com.healthcare.helix.userModule.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "medical_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_profile_id", nullable = false, unique = true)
    private UserProfile userProfile;

    private String bloodGroup;

    private Double height;

    private Double weight;

    @Column(length = 2000)
    private String allergies;

    @Column(length = 2000)
    private String currentMedications;

    @Column(length = 2000)
    private String medicalHistory;

    @OneToMany(mappedBy = "medicalProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicalCondition> medicalConditions = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // ---- Package-private setter, called only from UserProfile.setMedicalProfile() ----
    void setUserProfileInternal(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    // ---- Helper methods for MedicalCondition bidirectional consistency ----
    public void addMedicalCondition(MedicalCondition condition) {
        medicalConditions.add(condition);
        condition.setMedicalProfileInternal(this);
    }

    public void removeMedicalCondition(MedicalCondition condition) {
        medicalConditions.remove(condition);
        condition.setMedicalProfileInternal(null);
    }

}
