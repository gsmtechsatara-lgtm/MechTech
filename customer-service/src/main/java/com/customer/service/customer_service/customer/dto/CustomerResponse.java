package com.customer.service.customer_service.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {

    private Long customerCode;
    private Long merchantId;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String email;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String pincode;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

