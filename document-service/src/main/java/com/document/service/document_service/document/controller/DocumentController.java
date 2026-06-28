package com.document.service.document_service.document.controller;

import com.document.service.document_service.document.comman.ApiConstants;
import com.document.service.document_service.document.dto.response.ApiResponse;
import com.document.service.document_service.document.dto.response.DocumentResponse;
import com.document.service.document_service.document.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstants.BASE_URL)
public class DocumentController {

    private final DocumentService documentService;

   /* @PostMapping(ApiConstants.UPLOAD_DOCUMENT)
    public ResponseEntity<ApiResponse<DocumentResponse>> uploadDocument(
            @RequestPart("file") MultipartFile file,
            @RequestParam Long merchantId,
            @RequestParam Long referenceId,
            @RequestParam String moduleName) {

         DocumentResponse created = documentService.uploadDocument( , merchantId, referenceId, moduleName);
        return ResponseEntity.status(201).body(ApiResponse.<DocumentResponse>builder()
                .success(true)
                .message("Document uploaded successfully")
                .data(created)
                .build());
    } */

    @GetMapping(ApiConstants.GET_DOCUMENT)
    public ResponseEntity<ApiResponse<DocumentResponse>> getDocument(@PathVariable Long documentId) {
        DocumentResponse response = documentService.getDocument(documentId);
        return ResponseEntity.ok(ApiResponse.<DocumentResponse>builder()
                .success(true)
                .message("Document fetched successfully")
                .data(response)
                .build());
    }

    @GetMapping(ApiConstants.GET_BY_REFERENCE)
    public ResponseEntity<ApiResponse<List<DocumentResponse>>> getDocumentsByReference(
            @RequestParam Long referenceId,
            @RequestParam String moduleName) {

        List<DocumentResponse> response = documentService.getDocuments(referenceId, moduleName);
        return ResponseEntity.ok(ApiResponse.<List<DocumentResponse>>builder()
                .success(true)
                .message("Documents fetched successfully")
                .data(response)
                .build());
    }

    @PutMapping(ApiConstants.UPDATE_DOCUMENT)
    public ResponseEntity<ApiResponse<DocumentResponse>> updateDocument(
            @PathVariable Long documentId,
            @RequestPart("file") MultipartFile file) {

        DocumentResponse updated = documentService.updateDocument(documentId, file);
        return ResponseEntity.ok(ApiResponse.<DocumentResponse>builder()
                .success(true)
                .message("Document updated successfully")
                .data(updated)
                .build());
    }

    @DeleteMapping(ApiConstants.DELETE_DOCUMENT)
    public ResponseEntity<ApiResponse<Object>> deleteDocument(@PathVariable Long documentId) {
        documentService.deleteDocument(documentId);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message("Document deleted successfully")
                .data(null)
                .build());
    }

    @GetMapping(ApiConstants.DOWNLOAD_DOCUMENT)
    public ResponseEntity<ByteArrayResource> downloadDocument(@PathVariable Long documentId) {
        DocumentResponse metadata = documentService.getDocument(documentId);
        byte[] bytes = documentService.downloadDocument(documentId);

        ByteArrayResource resource = new ByteArrayResource(bytes);

        String contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + metadata.getOriginalFileName() + "\"")
                .body(resource);
    }
}

