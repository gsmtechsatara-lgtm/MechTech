package com.document.service.document_service.document.converter;

import com.document.service.document_service.document.comman.JobCardStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Ensures JobCard.status is always persisted as a valid Enum name (STRING)
 * and never fails reads when the DB contains invalid/empty/numeric/NULL values.
 */
@Converter(autoApply = false)
public class JobCardStatusConverter implements AttributeConverter<JobCardStatus, String> {

    /**
     * DB -> Enum
     */
    @Override
    public JobCardStatus convertToEntityAttribute(String dbValue) {
        if (dbValue == null) {
            return JobCardStatus.OPEN;
        }

        String normalized = dbValue.trim();
        if (normalized.isEmpty()) {
            return JobCardStatus.OPEN;
        }

        // Guard against legacy numeric values like "0", "1" etc.
        // We intentionally only accept valid enum names.
        try {
            return JobCardStatus.valueOf(normalized);
        } catch (IllegalArgumentException ex) {
            return JobCardStatus.OPEN;
        }
    }

    /**
     * Enum -> DB
     */
    @Override
    public String convertToDatabaseColumn(JobCardStatus attribute) {
        if (attribute == null) {
            return JobCardStatus.OPEN.name();
        }
        return attribute.name();
    }
}

