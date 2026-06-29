package com.document.service.document_service.document.repository;

import com.document.service.document_service.document.comman.JobCardStatus;
import com.document.service.document_service.document.entity.JobCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobCardRepository extends JpaRepository<JobCard, Long> {

    List<JobCard> findByCustomerId(Long customerId);

    List<JobCard> findByVehicleId(Long vehicleId);

    List<JobCard> findByMerchantId(Long merchantId);

    List<JobCard> findByStatus(JobCardStatus status);

    boolean existsByJobCardNumber(String jobCardNumber);
}

