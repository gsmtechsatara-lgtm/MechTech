package com.document.service.document_service.document.mapper;

import com.document.service.document_service.document.dto.response.JobCardResponse;
import com.document.service.document_service.document.entity.JobCard;
import org.springframework.stereotype.Component;

@Component
public class JobCardMapper {

    public JobCardResponse toResponse(JobCard jobCard) {
        if (jobCard == null) {
            return null;
        }

        return JobCardResponse.builder()
                .jobCardId(jobCard.getJobCardId())
                .merchantId(jobCard.getMerchantId())
                .customerId(jobCard.getCustomerId())
                .vehicleId(jobCard.getVehicleId())
                .jobCardNumber(jobCard.getJobCardNumber())
                .status(jobCard.getStatus())
                .complaint(jobCard.getComplaint())
                .diagnosis(jobCard.getDiagnosis())
                .workDescription(jobCard.getWorkDescription())
                .estimatedCost(jobCard.getEstimatedCost())
                .actualCost(jobCard.getActualCost())
                .estimatedDeliveryDate(jobCard.getEstimatedDeliveryDate())
                .deliveredDate(jobCard.getDeliveredDate())
                .assignedMechanicId(jobCard.getAssignedMechanicId())
                .odometerReading(jobCard.getOdometerReading())
                .fuelLevel(jobCard.getFuelLevel())
                .priority(jobCard.getPriority())
                .paymentStatus(jobCard.getPaymentStatus())
                .notes(jobCard.getNotes())
                .active(jobCard.getActive())
                .createdBy(jobCard.getCreatedBy())
                .updatedBy(jobCard.getUpdatedBy())
                .createdAt(jobCard.getCreatedAt())
                .updatedAt(jobCard.getUpdatedAt())
                .build();
    }
}

