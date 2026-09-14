package com.healthcare.helix.reportModule.service;

import com.healthcare.helix.reportModule.dto.response.DocumentResponse;
import com.healthcare.helix.reportModule.dto.response.DocumentUploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface DocumentService {
    DocumentUploadResponse uploadDocument(UUID userId, MultipartFile file, String category);

    List<DocumentResponse> getMyDocuments(UUID userId);

    DocumentResponse getDocumentById(UUID userId, UUID documentId);

    void deleteDocument(UUID userId, UUID documentId);
}
