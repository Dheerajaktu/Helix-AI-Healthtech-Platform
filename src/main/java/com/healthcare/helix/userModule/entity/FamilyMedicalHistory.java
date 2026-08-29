package com.healthcare.helix.userModule.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "family_medical_history")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FamilyMedicalHistory {
    @Id
    private UUID id;

    @ManyToOne
    private MedicalProfile medicalProfile;

    private String relation;         // "Father", "Mother", "Sibling"
    private String conditionName;    // "Diabetes", "Heart Disease"
}
