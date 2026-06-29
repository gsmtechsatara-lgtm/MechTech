package com.document.service.document_service.document.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateJobCardRequest {

    private String diagnosis;

    private String workDescription;

    private BigDecimal estimatedCost;

    private BigDecimal actualCost;

    private Long assignedMechanicId;

    private Long priority;

    private String paymentStatus;

    @NotNull(message = "status is required")
    private com.document.service.document_service.document.comman.JobCardStatus status;

    private String notes;

    @NotNull(message = "updatedAt is required")
    private LocalDateTime updatedAt;

    private Long updatedBy;
}

