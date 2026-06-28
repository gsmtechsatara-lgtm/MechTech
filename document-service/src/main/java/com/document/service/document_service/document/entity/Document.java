package com.document.service.document_service.document.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "documents")
@Data
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

