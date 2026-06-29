package com.document.service.document_service.document.service;

import com.document.service.document_service.document.dto.request.CreateJobCardRequest;
import com.document.service.document_service.document.dto.request.UpdateJobCardRequest;
import com.document.service.document_service.document.dto.response.JobCardResponse;
import com.document.service.document_service.document.comman.JobCardStatus;

import java.util.List;

public interface JobCardService {

    JobCardResponse createJobCard(CreateJobCardRequest request);

    JobCardResponse getJobCard(Long jobCardId);

    List<JobCardResponse> getAllJobCards();

    List<JobCardResponse> getJobCardsByCustomerId(Long customerId);

    List<JobCardResponse> getJobCardsByVehicleId(Long vehicleId);

    List<JobCardResponse> getJobCardsByMerchantId(Long merchantId);

    JobCardResponse updateJobCard(Long jobCardId, UpdateJobCardRequest request);

    void deleteJobCard(Long jobCardId);

    List<JobCardResponse> getJobCardsByStatus(JobCardStatus status);
}

