package com.document.service.document_service.document.service.impl;

import com.document.service.document_service.document.comman.JobCardStatus;
import com.document.service.document_service.document.dto.request.CreateJobCardRequest;
import com.document.service.document_service.document.dto.request.UpdateJobCardRequest;
import com.document.service.document_service.document.dto.response.JobCardResponse;
import com.document.service.document_service.document.entity.JobCard;
import com.document.service.document_service.document.exception.ResourceNotFoundException;
import com.document.service.document_service.document.mapper.JobCardMapper;
import com.document.service.document_service.document.repository.JobCardRepository;
import com.document.service.document_service.document.service.JobCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobCardServiceImpl implements JobCardService {

    private final JobCardRepository jobCardRepository;
    private final JobCardMapper jobCardMapper;

    @Override
    @Transactional
    public JobCardResponse createJobCard(CreateJobCardRequest request) {
        LocalDateTime now = LocalDateTime.now();

        JobCard jobCard = new JobCard();
        jobCard.setMerchantId(request.getMerchantId());
        jobCard.setCustomerId(request.getCustomerId());
        jobCard.setVehicleId(request.getVehicleId());

        String jobCardNumber = generateJobCardNumber(now.toLocalDate());
        // Extremely unlikely race; recheck for safety.
        while (jobCardRepository.existsByJobCardNumber(jobCardNumber)) {
            jobCardNumber = generateJobCardNumber(now.toLocalDate().plusDays(0));
        }
        jobCard.setJobCardNumber(jobCardNumber);

        jobCard.setStatus(JobCardStatus.OPEN);
        jobCard.setPaymentStatus("PENDING");
        jobCard.setActualCost(request.getActualCost() != null ? request.getActualCost() : java.math.BigDecimal.ZERO);

        jobCard.setActive(true);
        jobCard.setCreatedAt(now);
        jobCard.setUpdatedAt(now);
        jobCard.setCreatedBy(request.getCreatedBy());
        jobCard.setUpdatedBy(request.getCreatedBy());

        jobCard.setComplaint(request.getComplaint());
        jobCard.setDiagnosis(request.getDiagnosis());
        jobCard.setWorkDescription(request.getWorkDescription());
        jobCard.setEstimatedCost(request.getEstimatedCost());
        jobCard.setEstimatedDeliveryDate(request.getEstimatedDeliveryDate());
        jobCard.setAssignedMechanicId(request.getAssignedMechanicId());
        jobCard.setOdometerReading(request.getOdometerReading());
        jobCard.setFuelLevel(request.getFuelLevel());
        jobCard.setPriority(request.getPriority());
        jobCard.setNotes(request.getNotes());

        JobCard saved = jobCardRepository.save(jobCard);
        return jobCardMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public JobCardResponse getJobCard(Long jobCardId) {
        JobCard existing = jobCardRepository.findById(jobCardId)
                .orElseThrow(() -> new ResourceNotFoundException("Job card not found with id: " + jobCardId));

        if (Boolean.FALSE.equals(existing.getActive())) {
            throw new ResourceNotFoundException("Job card not found with id: " + jobCardId);
        }

        return jobCardMapper.toResponse(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobCardResponse> getAllJobCards() {
        return jobCardRepository.findAll().stream()
                .filter(j -> Boolean.TRUE.equals(j.getActive()))
                .map(jobCardMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobCardResponse> getJobCardsByCustomerId(Long customerId) {
        return jobCardRepository.findByCustomerId(customerId).stream()
                .filter(j -> Boolean.TRUE.equals(j.getActive()))
                .map(jobCardMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobCardResponse> getJobCardsByVehicleId(Long vehicleId) {
        return jobCardRepository.findByVehicleId(vehicleId).stream()
                .filter(j -> Boolean.TRUE.equals(j.getActive()))
                .map(jobCardMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobCardResponse> getJobCardsByMerchantId(Long merchantId) {
        return jobCardRepository.findByMerchantId(merchantId).stream()
                .filter(j -> Boolean.TRUE.equals(j.getActive()))
                .map(jobCardMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public JobCardResponse updateJobCard(Long jobCardId, UpdateJobCardRequest request) {
        JobCard existing = jobCardRepository.findById(jobCardId)
                .orElseThrow(() -> new ResourceNotFoundException("Job card not found with id: " + jobCardId));

        if (Boolean.FALSE.equals(existing.getActive())) {
            throw new ResourceNotFoundException("Job card not found with id: " + jobCardId);
        }

        // Editable fields only (jobCardNumber/createdAt/customerId/merchantId immutable)
        existing.setDiagnosis(request.getDiagnosis());
        existing.setWorkDescription(request.getWorkDescription());
        existing.setEstimatedCost(request.getEstimatedCost());
        existing.setActualCost(request.getActualCost() != null ? request.getActualCost() : existing.getActualCost());
        existing.setAssignedMechanicId(request.getAssignedMechanicId());
        existing.setPriority(request.getPriority());
        existing.setPaymentStatus(request.getPaymentStatus() != null ? request.getPaymentStatus() : existing.getPaymentStatus());
        existing.setStatus(request.getStatus());
        existing.setNotes(request.getNotes());
        existing.setUpdatedAt(request.getUpdatedAt() != null ? request.getUpdatedAt() : LocalDateTime.now());
        existing.setUpdatedBy(request.getUpdatedBy() != null ? request.getUpdatedBy() : existing.getUpdatedBy());

        JobCard updated = jobCardRepository.save(existing);
        return jobCardMapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void deleteJobCard(Long jobCardId) {
        JobCard existing = jobCardRepository.findById(jobCardId)
                .orElseThrow(() -> new ResourceNotFoundException("Job card not found with id: " + jobCardId));

        existing.setActive(false);
        existing.setUpdatedAt(LocalDateTime.now());
        jobCardRepository.save(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobCardResponse> getJobCardsByStatus(JobCardStatus status) {
        return jobCardRepository.findByStatus(status).stream()
                .filter(j -> Boolean.TRUE.equals(j.getActive()))
                .map(jobCardMapper::toResponse)
                .toList();
    }

    private String generateJobCardNumber(LocalDate date) {
        String datePart = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String prefix = "JC-" + datePart + "-";

        // Count existing cards for that day to build the sequence.
        // Matches required format: JC-YYYYMMDD-0001
        long count = jobCardRepository.findAll().stream()
                .filter(j -> j.getJobCardNumber() != null && j.getJobCardNumber().startsWith(prefix))
                .count();

        long next = count + 1;
        return prefix + String.format("%04d", next);
    }
}

