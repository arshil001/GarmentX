package com.garmentx.catalog.dto;

import com.garmentx.catalog.entity.TaxRateStatus;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TaxRateRequest {

    @NotNull(message = "Tax category ID is required")
    private Long taxCategoryId;

    @NotNull(message = "Tax rate is required")
    @DecimalMin(
            value = "0.000000",
            inclusive = true,
            message = "Tax rate cannot be negative"
    )
    @DecimalMax(
            value = "100.000000",
            inclusive = true,
            message = "Tax rate cannot exceed 100"
    )
    private BigDecimal rate;

    @NotNull(message = "Effective from date is required")
    private LocalDate effectiveFrom;

    private LocalDate effectiveTo;

    private TaxRateStatus status;

    public Long getTaxCategoryId() {
        return taxCategoryId;
    }

    public void setTaxCategoryId(Long taxCategoryId) {
        this.taxCategoryId = taxCategoryId;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public LocalDate getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(LocalDate effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public LocalDate getEffectiveTo() {
        return effectiveTo;
    }

    public void setEffectiveTo(LocalDate effectiveTo) {
        this.effectiveTo = effectiveTo;
    }

    public TaxRateStatus getStatus() {
        return status;
    }

    public void setStatus(TaxRateStatus status) {
        this.status = status;
    }
}