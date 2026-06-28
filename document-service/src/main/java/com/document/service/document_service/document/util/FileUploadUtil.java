package com.document.service.document_service.document.util;

import com.document.service.document_service.document.config.FileStorageProperties;
import com.document.service.document_service.document.exception.FileValidationException;
import com.document.service.document_service.document.exception.InvalidModuleNameException;
import com.document.service.document_service.document.service.impl.FileMetadata;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Component
public class FileUploadUtil {

    private static final long MAX_FILE_SIZE_BYTES = 10L * 1024L * 1024L; // 10MB

    private static final Set<String> SUPPORTED_EXTENSIONS = Set.of(
            "jpg", "jpeg", "png", "pdf", "doc", "docx"
    );

    private static final Map<String, String> MODULE_TO_FOLDER = Map.of(
            "CUSTOMER", "customer",
            "VEHICLE", "vehicle",
            "JOBCARD", "jobcard",
            "INVOICE", "invoice",
            "MERCHANT", "merchant",
            "INSURANCE", "insurance",
            "OTHER", "other"
    );

    private final FileStorageProperties fileStorageProperties;

    public FileUploadUtil(FileStorageProperties fileStorageProperties) {
        this.fileStorageProperties = fileStorageProperties;
    }

    public FileMetadata storeFile(MultipartFile file, Long merchantId, String moduleName) {
        if (file == null || file.isEmpty()) {
            throw new FileValidationException("File must be provided");
        }

        if (file.getSize() > MAX_FILE_SIZE_BYTES) {
            throw new FileValidationException("Maximum file size allowed is 10MB");
        }

        String originalFileName = file.getOriginalFilename();
        if (!StringUtils.hasText(originalFileName) || !originalFileName.contains(".")) {
            throw new FileValidationException("Invalid file name");
        }

        String extension = StringUtils.getFilenameExtension(originalFileName).toLowerCase();
        if (extension == null || !SUPPORTED_EXTENSIONS.contains(extension)) {
            throw new FileValidationException("Unsupported file type: " + extension);
        }

        String normalizedModuleName = moduleName == null ? "" : moduleName.trim().toUpperCase();
        String folder = MODULE_TO_FOLDER.get(normalizedModuleName);
        if (folder == null) {
            throw new InvalidModuleNameException("Invalid moduleName: " + moduleName);
        }

        String uuid = UUID.randomUUID().toString();
        String documentName = uuid + "_" + StringUtils.replace(originalFileName, " ", "_");

        Path targetDir = Paths.get(fileStorageProperties.getBaseDir(), folder);
        try {
            Files.createDirectories(targetDir);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create upload directory", e);
        }

        Path targetFile = targetDir.resolve(documentName);
        try {
            Files.copy(file.getInputStream(), targetFile);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }

        String relativeFilePath = Paths.get(folder, documentName).toString().replace("\\", "/");
        String fileUrl;
        if (fileStorageProperties.getPublicBaseUrl() != null && !fileStorageProperties.getPublicBaseUrl().isBlank()) {
            fileUrl = fileStorageProperties.getPublicBaseUrl().replaceAll("/$", "") + "/" + relativeFilePath;
        } else {
            fileUrl = relativeFilePath;
        }

        String fileType = file.getContentType();

        return FileMetadata.builder()
                .documentName(documentName)
                .originalFileName(originalFileName)
                .fileType(fileType)
                .filePath(relativeFilePath)
                .fileUrl(fileUrl)
                .fileSize(file.getSize())
                .extension(extension)
                .build();
    }

    public void deletePhysicalFileIfEnabled(String relativeFilePath) {
        if (relativeFilePath == null || relativeFilePath.isBlank()) {
            return;
        }

        if (!fileStorageProperties.isPhysicalDeleteEnabled()) {
            return;
        }

        Path fileOnDisk = Paths.get(fileStorageProperties.getBaseDir(), relativeFilePath);
        try {
            Files.deleteIfExists(fileOnDisk);
        } catch (IOException e) {
            // Do not fail business flow; just swallow or log
            throw new RuntimeException("Failed to delete physical file", e);
        }
    }

    public byte[] readPhysicalFile(String relativeFilePath) {
        if (relativeFilePath == null || relativeFilePath.isBlank()) {
            throw new FileValidationException("File path not found");
        }

        Path fileOnDisk = Paths.get(fileStorageProperties.getBaseDir(), relativeFilePath);
        try {
            return Files.readAllBytes(fileOnDisk);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file", e);
        }
    }

    public String resolveContentType(String relativeFilePath) {
        if (relativeFilePath == null) {
            return "application/octet-stream";
        }
        String extension = "";
        int idx = relativeFilePath.lastIndexOf('.');
        if (idx >= 0) {
            extension = relativeFilePath.substring(idx + 1).toLowerCase();
        }

        if ("jpg".equals(extension) || "jpeg".equals(extension)) {
            return "image/jpeg";
        }
        if ("png".equals(extension)) {
            return "image/png";
        }
        if ("pdf".equals(extension)) {
            return "application/pdf";
        }
        if ("doc".equals(extension)) {
            return "application/msword";
        }
        if ("docx".equals(extension)) {
            return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        }
        return "application/octet-stream";
    }
}

