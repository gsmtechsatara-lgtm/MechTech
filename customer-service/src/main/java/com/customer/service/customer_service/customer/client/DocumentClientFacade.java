package com.customer.service.customer_service.customer.client;

import com.customer.service.customer_service.customer.client.dto.ApiResponse;
import com.customer.service.customer_service.customer.client.dto.DocumentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentClientFacade {

    private final DocumentServiceClient documentServiceClient;

    public List<DocumentResponse> getDocumentsByReference(Long referenceId, String moduleName) {
        ApiResponse<List<DocumentResponse>> resp = documentServiceClient.getDocumentsByReference(referenceId, moduleName);
        return resp != null ? resp.getData() : null;
    }
}

