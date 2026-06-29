package com.document.service.document_service.document.client;

import com.document.service.document_service.document.client.dto.ApiResponse;
import com.document.service.document_service.document.client.dto.CustomerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service", path = "/api/v1/customers")
public interface CustomerServiceClient {

    @GetMapping("/{customerCode}")
    ApiResponse<CustomerResponse> getCustomer(@PathVariable("customerCode") Long customerCode);
}

