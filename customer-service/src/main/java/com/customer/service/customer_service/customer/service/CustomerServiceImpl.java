package com.customer.service.customer_service.customer.service;

import com.customer.service.customer_service.customer.dto.CustomerResponse;
import com.customer.service.customer_service.customer.dto.CreateCustomerRequest;
import com.customer.service.customer_service.customer.dto.UpdateCustomerRequest;
import com.customer.service.customer_service.customer.entity.Customer;
import com.customer.service.customer_service.customer.exception.DuplicateMobileNumberException;
import com.customer.service.customer_service.customer.exception.ResourceNotFoundException;
import com.customer.service.customer_service.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public CustomerResponse createCustomer(CreateCustomerRequest request) {
        if (customerRepository.existsByMobileNumber(request.getMobileNumber())) {
            throw new DuplicateMobileNumberException("Mobile number already exists: " + request.getMobileNumber());
        }

        LocalDateTime now = LocalDateTime.now();

        Customer customer = new Customer();
        customer.setMerchantId(request.getMerchantId());
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setMobileNumber(request.getMobileNumber());
        customer.setEmail(request.getEmail());
        customer.setAddressLine1(request.getAddressLine1());
        customer.setAddressLine2(request.getAddressLine2());
        customer.setCity(request.getCity());
        customer.setState(request.getState());
        customer.setPincode(request.getPincode());
        customer.setActive(true);
        customer.setCreatedAt(now);
        customer.setUpdatedAt(now);

        Customer saved = customerRepository.save(customer);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public CustomerResponse updateCustomer(Long customerCode, UpdateCustomerRequest request) {
        Customer existing = customerRepository.findById(customerCode)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with code: " + customerCode));

        boolean mobileChanged = !existing.getMobileNumber().equals(request.getMobileNumber());
        if (mobileChanged) {
            customerRepository.findByMobileNumber(request.getMobileNumber()).ifPresent(other -> {
                if (!other.getCustomerCode().equals(customerCode)) {
                    throw new DuplicateMobileNumberException("Mobile number already exists: " + request.getMobileNumber());
                }
            });
        }

        existing.setMerchantId(request.getMerchantId());
        existing.setFirstName(request.getFirstName());
        existing.setLastName(request.getLastName());
        existing.setMobileNumber(request.getMobileNumber());
        existing.setEmail(request.getEmail());
        existing.setAddressLine1(request.getAddressLine1());
        existing.setAddressLine2(request.getAddressLine2());
        existing.setCity(request.getCity());
        existing.setState(request.getState());
        existing.setPincode(request.getPincode());
        existing.setUpdatedAt(LocalDateTime.now());

        Customer updated = customerRepository.save(existing);
        return toResponse(updated);
    }

    @Override
    @Transactional
    public void deleteCustomer(Long customerCode) {
        Customer existing = customerRepository.findById(customerCode)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with code: " + customerCode));
        customerRepository.delete(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponse getCustomer(Long customerCode) {
        Customer existing = customerRepository.findById(customerCode)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with code: " + customerCode));
        return toResponse(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponse> getCustomersByMerchant(Long merchantId) {
        return customerRepository.findByMerchantId(merchantId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private CustomerResponse toResponse(Customer customer) {
        return CustomerResponse.builder()
                .customerCode(customer.getCustomerCode())
                .merchantId(customer.getMerchantId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .mobileNumber(customer.getMobileNumber())
                .email(customer.getEmail())
                .addressLine1(customer.getAddressLine1())
                .addressLine2(customer.getAddressLine2())
                .city(customer.getCity())
                .state(customer.getState())
                .pincode(customer.getPincode())
                .active(customer.getActive())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .build();
    }
}

