package com.document.service.document_service.document.service;

import com.document.service.document_service.document.dto.response.DocumentResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentService {

    DocumentResponse uploadDocument(
            MultipartFile file,
            Long merchantId,
            Long referenceId,
            String moduleName);

    DocumentResponse getDocument(Long documentId);

    List<DocumentResponse> getDocuments(Long referenceId, String moduleName);

    void deleteDocument(Long documentId);

    DocumentResponse updateDocument(Long documentId, MultipartFile file);

    byte[] downloadDocument(Long documentId);
}

