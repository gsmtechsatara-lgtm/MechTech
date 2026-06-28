package com.document.service.document_service.document.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentResponse {

    private Long documentId;

    private Long merchantId;

    private Long referenceId;

    private String moduleName;

    private String documentName;

    private String originalFileName;

    private String fileType;

    private String filePath;

    private String fileUrl;

    private Long fileSize;

    private Boolean active;

    private LocalDateTime uploadedAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

