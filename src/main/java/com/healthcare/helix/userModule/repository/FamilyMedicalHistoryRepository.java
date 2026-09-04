package com.healthcare.helix.userModule.repository;

import com.healthcare.helix.userModule.entity.FamilyMedicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface FamilyMedicalHistoryRepository extends JpaRepository<FamilyMedicalHistory, Long> {

    @Query("SELECT fh FROM FamilyMedicalHistory fh WHERE fh.medicalProfile.id = :medicalProfileId")
    List<FamilyMedicalHistory> findByMedicalProfileId(@Param("medicalProfileId") UUID medicalProfileId);
}
