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
@Table(name = "emergency_contact")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmergencyContact {
    @Id
    private UUID id;

    @ManyToOne
    private UserProfile userProfile;

    private String name;
    private String relation;
    private String mobile;
}
