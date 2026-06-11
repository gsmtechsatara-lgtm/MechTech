package com.customer.service.customer_service.customer.service;

import com.customer.service.customer_service.customer.dto.CustomerResponse;
import com.customer.service.customer_service.customer.dto.CreateCustomerRequest;
import com.customer.service.customer_service.customer.dto.UpdateCustomerRequest;

import java.util.List;

public interface CustomerService {

    CustomerResponse createCustomer(CreateCustomerRequest request);

    CustomerResponse updateCustomer(Long customerCode, UpdateCustomerRequest request);

    void deleteCustomer(Long customerCode);

    CustomerResponse getCustomer(Long customerCode);

    List<CustomerResponse> getCustomersByMerchant(Long merchantId);
}

