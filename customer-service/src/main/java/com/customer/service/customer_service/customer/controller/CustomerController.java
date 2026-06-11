package com.customer.service.customer_service.customer.controller;

import com.customer.service.customer_service.customer.comman.ApiConstants;
import com.customer.service.customer_service.customer.dto.ApiResponse;
import com.customer.service.customer_service.customer.dto.CustomerResponse;
import com.customer.service.customer_service.customer.dto.CreateCustomerRequest;
import com.customer.service.customer_service.customer.dto.UpdateCustomerRequest;
import com.customer.service.customer_service.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstants.API_BASE)
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping(ApiConstants.CREATE_CUSTOMER)
    public ResponseEntity<ApiResponse<CustomerResponse>> createCustomer(@Valid @RequestBody CreateCustomerRequest request) {
        CustomerResponse created = customerService.createCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<CustomerResponse>builder()
                        .success(true)
                        .message("Customer created successfully")
                        .data(created)
                        .build()
        );
    }

    @PutMapping(ApiConstants.UPDATE_CUSTOMER)
    public ResponseEntity<ApiResponse<CustomerResponse>> updateCustomer(
            @PathVariable Long customerCode,
            @Valid @RequestBody UpdateCustomerRequest request) {
        CustomerResponse updated = customerService.updateCustomer(customerCode, request);
        return ResponseEntity.ok(
                ApiResponse.<CustomerResponse>builder()
                        .success(true)
                        .message("Customer updated successfully")
                        .data(updated)
                        .build()
        );
    }

    @DeleteMapping(ApiConstants.DELETE_CUSTOMER)
    public ResponseEntity<ApiResponse<Object>> deleteCustomer(@PathVariable Long customerCode) {

        customerService.deleteCustomer(customerCode);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Customer deleted successfully")
                        .data(null)
                        .build()
        );
    }

    @GetMapping(ApiConstants.GET_CUSTOMER)
    public ResponseEntity<ApiResponse<CustomerResponse>> getCustomer(@PathVariable Long customerCode) {
        CustomerResponse customer = customerService.getCustomer(customerCode);
        return ResponseEntity.ok(
                ApiResponse.<CustomerResponse>builder()
                        .success(true)
                        .message("Customer fetched successfully")
                        .data(customer)
                        .build()
        );
    }

    @GetMapping(ApiConstants.GET_CUSTOMER_LIST)
    public ResponseEntity<ApiResponse<List<CustomerResponse>>> getCustomersByMerchant(@RequestParam Long merchantId) {
        List<CustomerResponse> customers = customerService.getCustomersByMerchant(merchantId);
        return ResponseEntity.ok(
                ApiResponse.<List<CustomerResponse>>builder()
                        .success(true)
                        .message("Customers fetched successfully")
                        .data(customers)
                        .build()
        );
    }
}

