package com.document.service.document_service.document.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "document-service.storage")
public class FileStorageProperties {

    private String baseDir;

    private boolean physicalDeleteEnabled;

    private String publicBaseUrl;
}

