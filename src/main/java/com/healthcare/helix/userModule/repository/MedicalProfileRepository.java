package com.healthcare.helix.userModule.repository;

import com.healthcare.helix.userModule.entity.MedicalProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface MedicalProfileRepository extends JpaRepository<MedicalProfile, UUID> {

    @Query("SELECT mp FROM MedicalProfile mp WHERE mp.userProfile.userId = :userId")
    Optional<MedicalProfile> findByUserId(@Param("userId") UUID userId);

}
