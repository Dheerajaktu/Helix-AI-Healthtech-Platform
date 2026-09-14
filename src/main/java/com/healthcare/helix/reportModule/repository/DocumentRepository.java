package com.healthcare.helix.reportModule.repository;

import com.healthcare.helix.reportModule.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DocumentRepository extends JpaRepository<Document, Integer> {

    List<Document> findByUserId(UUID userId);

    Optional<Document> findByIdAndUserId(UUID id, UUID userId);
}
