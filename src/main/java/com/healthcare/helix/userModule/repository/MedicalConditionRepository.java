package com.healthcare.helix.userModule.repository;

import com.healthcare.helix.userModule.entity.MedicalCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MedicalConditionRepository extends JpaRepository<MedicalCondition, UUID> {

    @Query("SELECT mc FROM MedicalCondition mc WHERE mc.medicalProfile.id = :medicalProfileId")
    List<MedicalCondition> findByMedicalProfileId(@Param("medicalProfileId") UUID medicalProfileId);


    @Query("SELECT mc FROM MedicalCondition mc WHERE mc.id = :id AND mc.medicalProfile.id = :medicalProfileId")
    Optional<MedicalCondition> findByIdAndMedicalProfileId(@Param("id") UUID id, @Param("medicalProfileId") UUID medicalProfileId);

}
