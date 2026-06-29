package com.document.service.document_service.document.entity;

import com.document.service.document_service.document.comman.JobCardStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "jobCards")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobCardId;

    private Long merchantId;
    private Long customerId;
    private Long vehicleId;
    private String jobCardNumber;
    private JobCardStatus status;
    private String complaint;
    private String diagnosis;
    private String workDescription;
    private BigDecimal estimatedCost;
    private BigDecimal actualCost;
    private LocalDateTime estimatedDeliveryDate;
    private LocalDateTime deliveredDate;
    private Long assignedMechanicId;
    private Long odometerReading;
    private Long fuelLevel;
    private Long priority;
    private String paymentStatus;
    private String notes;
    private Boolean active;
    private Long createdBy;
    private Long updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}