package com.document.service.document_service.document.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateJobCardRequest {

    @NotNull(message = "merchantId is required")
    private Long merchantId;

    @NotNull(message = "customerId is required")
    private Long customerId;

    @NotNull(message = "vehicleId is required")
    private Long vehicleId;

    @NotBlank(message = "complaint is required")
    private String complaint;

    private String diagnosis;

    private String workDescription;

    private BigDecimal estimatedCost;

    private BigDecimal actualCost;

    private LocalDateTime estimatedDeliveryDate;

    private Long assignedMechanicId;

    private Long odometerReading;

    private Long fuelLevel;

    private Long priority;

    private String paymentStatus;

    private String notes;

    private Long createdBy;
}

