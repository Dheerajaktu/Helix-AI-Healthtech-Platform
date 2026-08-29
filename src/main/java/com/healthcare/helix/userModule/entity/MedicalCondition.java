package com.healthcare.helix.userModule.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "medical_condition")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedicalCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "medical_profile_id", nullable = false)
    private MedicalProfile medicalProfile;

    @Column(nullable = false)
    private String conditionName;

    @Column(length = 2000)
    private String description;
    private LocalDate diagnosedDate;
    private boolean active;

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

    // ---- Package-private setter, called only from MedicalProfile helper methods ----
    void setMedicalProfileInternal(MedicalProfile medicalProfile) {
        this.medicalProfile = medicalProfile;
    }
}
