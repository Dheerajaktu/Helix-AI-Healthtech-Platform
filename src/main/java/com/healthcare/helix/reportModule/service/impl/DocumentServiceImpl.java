package com.healthcare.helix.reportModule.service.impl;

import com.healthcare.helix.common.exception.ResourceNotFoundException;
import com.healthcare.helix.reportModule.dto.response.DocumentResponse;
import com.healthcare.helix.reportModule.dto.response.DocumentUploadResponse;
import com.healthcare.helix.reportModule.entity.Document;
import com.healthcare.helix.reportModule.repository.DocumentRepository;
import com.healthcare.helix.reportModule.service.CloudStorageService;
import com.healthcare.helix.reportModule.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final CloudStorageService cloudStorageService;

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10 MB

    @Override
    public DocumentUploadResponse uploadDocument(UUID userId, MultipartFile file, String category) {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be empty");
        }
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("File size exceeds 10MB limit");
        }

        String contentType = file.getContentType();
        if (contentType == null || !(contentType.equals("application/pdf")
                || contentType.startsWith("image/")
                || contentType.equals("text/csv"))) {
            throw new IllegalArgumentException("Only PDF, image, and CSV files are allowed");
        }

        String storageKey = cloudStorageService.uploadFile(file, userId);

        Document document = Document.builder()
                .userId(userId)
                .fileName(file.getOriginalFilename())
                .fileUrl(storageKey)
                .fileType(contentType)
                .fileSizeBytes(file.getSize())
                .documentCategory(category != null ? category : "OTHER")
                .build();

        Document saved = documentRepository.save(document);
        log.info("Document uploaded for userId: {}, documentId: {}", userId, saved.getId());

        return DocumentUploadResponse.builder()
                .documentId(saved.getId())
                .fileName(saved.getFileName())
                .documentCategory(saved.getDocumentCategory())
                .uploadedAt(saved.getUploadedAt())
                .build();
    }

    @Override
    public List<DocumentResponse> getMyDocuments(UUID userId) {
        return documentRepository.findByUserId(userId).stream()
                .map(doc -> DocumentResponse.builder()
                        .id(doc.getId())
                        .fileName(doc.getFileName())
                        .fileType(doc.getFileType())
                        .fileSizeBytes(doc.getFileSizeBytes())
                        .documentCategory(doc.getDocumentCategory())
                        .uploadedAt(doc.getUploadedAt())
                        .build())
                .toList();
    }

    @Override
    public DocumentResponse getDocumentById(UUID userId, UUID documentId) {
        Document doc = documentRepository.findByIdAndUserId(documentId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found"));

        String presignedUrl = cloudStorageService.generatePresignedUrl(doc.getFileUrl());

        return DocumentResponse.builder()
                .id(doc.getId())
                .fileName(doc.getFileName())
                .fileType(doc.getFileType())
                .fileSizeBytes(doc.getFileSizeBytes())
                .documentCategory(doc.getDocumentCategory())
                .uploadedAt(doc.getUploadedAt())
                .downloadUrl(presignedUrl)
                .build();
    }

    @Override
    public void deleteDocument(UUID userId, UUID documentId) {
        Document doc = documentRepository.findByIdAndUserId(documentId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found"));

        cloudStorageService.deleteFile(doc.getFileUrl());
        documentRepository.delete(doc);
        log.info("Document deleted: {}", documentId);
    }
}