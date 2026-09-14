package com.healthcare.helix.reportModule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentResponse {

    private UUID id;
    private String fileName;
    private String fileType;
    private Long fileSizeBytes;
    private String documentCategory;
    private LocalDateTime uploadedAt;
    private String downloadUrl;   // presigned URL
}
