package com.healthcare.helix.userModule.repository;

import com.healthcare.helix.userModule.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {


    boolean existsByUserId(UUID userId);
    Optional<UserProfile> findByUserId(UUID userId);




    @Query("SELECT up FROM UserProfile up LEFT JOIN FETCH up.medicalProfile")
    List<UserProfile> findAllWithMedicalProfile();


}
