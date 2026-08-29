package com.healthcare.helix.userModule.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "consent_record")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConsentRecord {
    /*  IMP NOTE -
    *   In India medical data consider sensitive personal data under -- DPDP Act (Digital Personal Data Protection Act)
    *   So for this I need explicit consent tracking from user.
    *   So this entity is all about this.
    * */

    @Id
    private UUID id;

    private UUID userProfileId;
    private String consentType;      // "DATA_PROCESSING", "AI_ANALYSIS", "DATA_SHARING"
    private boolean granted;
    private LocalDateTime grantedAt;
    private LocalDateTime revokedAt;
    private String consentVersion;   // policy version when took consent
}
