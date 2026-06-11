package com.customer.service.customer_service.customer.repository;

import com.customer.service.customer_service.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByMobileNumber(String mobileNumber);

    List<Customer> findByMerchantId(Long merchantId);

    boolean existsByMobileNumber(String mobileNumber);
}

