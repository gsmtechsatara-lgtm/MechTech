package com.document.service.document_service.document.mapper;

import com.document.service.document_service.document.dto.response.DocumentResponse;
import com.document.service.document_service.document.entity.Document;
import org.springframework.stereotype.Component;

@Component
public class DocumentMapper {

    public DocumentResponse toResponse(Document document) {
        if (document == null) {
            return null;
        }

        return DocumentResponse.builder()
                .documentId(document.getDocumentId())
                .merchantId(document.getMerchantId())
                .referenceId(document.getReferenceId())
                .moduleName(document.getModuleName())
                .documentName(document.getDocumentName())
                .originalFileName(document.getOriginalFileName())
                .fileType(document.getFileType())
                .filePath(document.getFilePath())
                .fileUrl(document.getFileUrl())
                .fileSize(document.getFileSize())
                .active(document.getActive())
                .uploadedAt(document.getUploadedAt())
                .createdAt(document.getCreatedAt())
                .updatedAt(document.getUpdatedAt())
                .build();
    }
}

