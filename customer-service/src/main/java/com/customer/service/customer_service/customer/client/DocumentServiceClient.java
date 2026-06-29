package com.customer.service.customer_service.customer.client;

import com.customer.service.customer_service.customer.client.dto.ApiResponse;
import com.customer.service.customer_service.customer.client.dto.DocumentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "document-service", path = "/api/v1/documents")
public interface DocumentServiceClient {

    @GetMapping("/{documentId}")
    ApiResponse<DocumentResponse> getDocument(@PathVariable Long documentId);

    @GetMapping("/reference")
    ApiResponse<List<DocumentResponse>> getDocumentsByReference(
            @RequestParam("referenceId") Long referenceId,
            @RequestParam("moduleName") String moduleName);
}

