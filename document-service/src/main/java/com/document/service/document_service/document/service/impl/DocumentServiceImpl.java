package com.document.service.document_service.document.service.impl;

import com.document.service.document_service.document.dto.response.DocumentResponse;
import com.document.service.document_service.document.entity.Document;
import com.document.service.document_service.document.exception.ResourceNotFoundException;
import com.document.service.document_service.document.mapper.DocumentMapper;
import com.document.service.document_service.document.repository.DocumentRepository;
import com.document.service.document_service.document.service.DocumentService;
import com.document.service.document_service.document.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;
    private final FileUploadUtil fileUploadUtil;

    @Override
    @Transactional
    public DocumentResponse uploadDocument(MultipartFile file, Long merchantId, Long referenceId, String moduleName) {
        LocalDateTime now = LocalDateTime.now();

        // Store physical + get metadata
        FileMetadata metadata = fileUploadUtil.storeFile(file, merchantId, moduleName);

        Document document = new Document();
        document.setMerchantId(merchantId);
        document.setReferenceId(referenceId);
        document.setModuleName(moduleName);
        document.setDocumentName(metadata.getDocumentName());
        document.setOriginalFileName(metadata.getOriginalFileName());
        document.setFileType(metadata.getFileType());
        document.setFilePath(metadata.getFilePath());
        document.setFileUrl(metadata.getFileUrl());
        document.setFileSize(metadata.getFileSize());
        document.setActive(true);
        document.setUploadedAt(now);
        document.setCreatedAt(now);
        document.setUpdatedAt(now);

        Document saved = documentRepository.save(document);
        return documentMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentResponse getDocument(Long documentId) {
        Document existing = documentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with id: " + documentId));

        if (Boolean.FALSE.equals(existing.getActive())) {
            throw new ResourceNotFoundException("Document not found with id: " + documentId);
        }

        return documentMapper.toResponse(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentResponse> getDocuments(Long referenceId, String moduleName) {
        return documentRepository.findByReferenceIdAndModuleName(referenceId, moduleName)
                .stream()
                .filter(d -> Boolean.TRUE.equals(d.getActive()))
                .map(documentMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public void deleteDocument(Long documentId) {
        Document existing = documentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with id: " + documentId));

        existing.setActive(false);
        existing.setUpdatedAt(LocalDateTime.now());
        documentRepository.save(existing);

        // physical delete only if configured
        fileUploadUtil.deletePhysicalFileIfEnabled(existing.getFilePath());
    }

    @Override
    @Transactional
    public DocumentResponse updateDocument(Long documentId, MultipartFile file) {
        Document existing = documentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with id: " + documentId));

        if (Boolean.FALSE.equals(existing.getActive())) {
            throw new ResourceNotFoundException("Document not found with id: " + documentId);
        }

        // Delete old file only if configured
        fileUploadUtil.deletePhysicalFileIfEnabled(existing.getFilePath());

        // Store new file under same module
        FileMetadata metadata = fileUploadUtil.storeFile(file, existing.getMerchantId(), existing.getModuleName());

        existing.setDocumentName(metadata.getDocumentName());
        existing.setOriginalFileName(metadata.getOriginalFileName());
        existing.setFileType(metadata.getFileType());
        existing.setFilePath(metadata.getFilePath());
        existing.setFileUrl(metadata.getFileUrl());
        existing.setFileSize(metadata.getFileSize());
        existing.setUploadedAt(LocalDateTime.now());
        existing.setUpdatedAt(LocalDateTime.now());

        Document updated = documentRepository.save(existing);
        return documentMapper.toResponse(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] downloadDocument(Long documentId) {
        Document existing = documentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with id: " + documentId));

        if (Boolean.FALSE.equals(existing.getActive())) {
            throw new ResourceNotFoundException("Document not found with id: " + documentId);
        }

        return fileUploadUtil.readPhysicalFile(existing.getFilePath());
    }
}

