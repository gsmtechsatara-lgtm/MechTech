package com.document.service.document_service.document.controller;

import com.document.service.document_service.document.comman.JobCardApiConstants;
import com.document.service.document_service.document.comman.JobCardStatus;


import com.document.service.document_service.document.dto.request.CreateJobCardRequest;
import com.document.service.document_service.document.dto.request.UpdateJobCardRequest;
import com.document.service.document_service.document.dto.response.ApiResponse;
import com.document.service.document_service.document.dto.response.JobCardResponse;
import com.document.service.document_service.document.service.JobCardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(JobCardApiConstants.JOB_CARD_BASE_URL)

public class JobCardController {

    private final JobCardService jobCardService;

    @PostMapping(JobCardApiConstants.CREATE_JOB_CARD)

    public ResponseEntity<ApiResponse<JobCardResponse>> createJobCard(@RequestBody CreateJobCardRequest request) {
        JobCardResponse created = jobCardService.createJobCard(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<JobCardResponse>builder()
                        .success(true)
                        .message("Job Card created successfully")
                        .data(created)
                        .build()
        );
    }

    @GetMapping(JobCardApiConstants.GET_JOB_CARD)

    public ResponseEntity<ApiResponse<JobCardResponse>> getJobCard(@PathVariable Long jobCardId) {
        JobCardResponse response = jobCardService.getJobCard(jobCardId);
        return ResponseEntity.ok(ApiResponse.<JobCardResponse>builder()
                .success(true)
                .message("Job Card fetched successfully")
                .data(response)
                .build());
    }

    @GetMapping(JobCardApiConstants.GET_ALL_JOB_CARDS)

    public ResponseEntity<ApiResponse<List<JobCardResponse>>> getAllJobCards() {
        List<JobCardResponse> responses = jobCardService.getAllJobCards();
        return ResponseEntity.ok(ApiResponse.<List<JobCardResponse>>builder()
                .success(true)
                .message("Job Cards fetched successfully")
                .data(responses)
                .build());
    }

    @GetMapping(JobCardApiConstants.GET_JOB_CARDS_BY_CUSTOMER)

    public ResponseEntity<ApiResponse<List<JobCardResponse>>> getJobCardsByCustomer(@PathVariable Long customerId) {
        List<JobCardResponse> responses = jobCardService.getJobCardsByCustomerId(customerId);
        return ResponseEntity.ok(ApiResponse.<List<JobCardResponse>>builder()
                .success(true)
                .message("Job Cards fetched successfully")
                .data(responses)
                .build());
    }

    @GetMapping(JobCardApiConstants.GET_JOB_CARDS_BY_VEHICLE)

    public ResponseEntity<ApiResponse<List<JobCardResponse>>> getJobCardsByVehicle(@PathVariable Long vehicleId) {
        List<JobCardResponse> responses = jobCardService.getJobCardsByVehicleId(vehicleId);
        return ResponseEntity.ok(ApiResponse.<List<JobCardResponse>>builder()
                .success(true)
                .message("Job Cards fetched successfully")
                .data(responses)
                .build());
    }

    @GetMapping(JobCardApiConstants.GET_JOB_CARDS_BY_MERCHANT)

    public ResponseEntity<ApiResponse<List<JobCardResponse>>> getJobCardsByMerchant(@PathVariable Long merchantId) {
        List<JobCardResponse> responses = jobCardService.getJobCardsByMerchantId(merchantId);
        return ResponseEntity.ok(ApiResponse.<List<JobCardResponse>>builder()
                .success(true)
                .message("Job Cards fetched successfully")
                .data(responses)
                .build());
    }

    @PutMapping(JobCardApiConstants.UPDATE_JOB_CARD)

    public ResponseEntity<ApiResponse<JobCardResponse>> updateJobCard(
            @PathVariable Long jobCardId,
            @Valid @RequestBody UpdateJobCardRequest request) {
        JobCardResponse updated = jobCardService.updateJobCard(jobCardId, request);
        return ResponseEntity.ok(ApiResponse.<JobCardResponse>builder()
                .success(true)
                .message("Job Card updated successfully")
                .data(updated)
                .build());
    }

    @DeleteMapping(JobCardApiConstants.DELETE_JOB_CARD)

    public ResponseEntity<ApiResponse<Object>> deleteJobCard(@PathVariable Long jobCardId) {
        jobCardService.deleteJobCard(jobCardId);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message("Job Card deleted successfully")
                .data(null)
                .build());
    }
}

