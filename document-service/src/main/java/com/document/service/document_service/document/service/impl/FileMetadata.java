package com.document.service.document_service.document.service.impl;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileMetadata {
    private String documentName;
    private String originalFileName;
    private String fileType;
    private String filePath;
    private String fileUrl;
    private Long fileSize;
    private String extension;
}

