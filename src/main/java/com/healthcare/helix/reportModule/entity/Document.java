package com.healthcare.helix.reportModule.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID userId;   // cross-module reference

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false, length = 1000)
    private String fileUrl;   // R2 storage key/path

    @Column(nullable = false)
    private String fileType;   // PDF, JPG, PNG, CSV

    private Long fileSizeBytes;

    private String documentCategory;   // "ID_PROOF", "LAB_REPORT", "PRESCRIPTION", "OTHER"

    @Column(nullable = false, updatable = false)
    private LocalDateTime uploadedAt;

    @PrePersist
    protected void onCreate() {
        uploadedAt = LocalDateTime.now();
    }
}
