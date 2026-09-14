package com.healthcare.helix.reportModule.controller;


import com.healthcare.helix.reportModule.dto.response.DocumentResponse;
import com.healthcare.helix.reportModule.dto.response.DocumentUploadResponse;
import com.healthcare.helix.reportModule.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<DocumentUploadResponse> uploadDocument(
            Authentication authentication,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "category", required = false) String category) {

        UUID userId = (UUID) authentication.getPrincipal();
        return ResponseEntity.ok(documentService.uploadDocument(userId, file, category));
    }

    @GetMapping
    public ResponseEntity<List<DocumentResponse>> getMyDocuments(Authentication authentication) {
        UUID userId = (UUID) authentication.getPrincipal();
        return ResponseEntity.ok(documentService.getMyDocuments(userId));
    }

    @GetMapping("/{documentId}")
    public ResponseEntity<DocumentResponse> getDocumentById(
            Authentication authentication,
            @PathVariable UUID documentId) {

        UUID userId = (UUID) authentication.getPrincipal();
        return ResponseEntity.ok(documentService.getDocumentById(userId, documentId));
    }

    @DeleteMapping("/{documentId}")
    public ResponseEntity<Void> deleteDocument(
            Authentication authentication,
            @PathVariable UUID documentId) {

        UUID userId = (UUID) authentication.getPrincipal();
        documentService.deleteDocument(userId, documentId);
        return ResponseEntity.noContent().build();
    }
}
