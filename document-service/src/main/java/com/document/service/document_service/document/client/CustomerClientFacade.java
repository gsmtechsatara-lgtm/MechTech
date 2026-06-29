package com.document.service.document_service.document.client;

import com.document.service.document_service.document.client.dto.CustomerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerClientFacade {

    private final CustomerServiceClient customerServiceClient;

    public CustomerResponse getCustomer(Long customerCode) {
        return customerServiceClient.getCustomer(customerCode).getData();
    }
}

