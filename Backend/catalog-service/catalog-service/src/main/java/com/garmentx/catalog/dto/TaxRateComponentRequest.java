package com.garmentx.catalog.dto;

import com.garmentx.catalog.entity.TaxComponentType;
import com.garmentx.catalog.entity.TaxRateComponentStatus;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class TaxRateComponentRequest {

    @NotNull(message = "Tax rate ID is required")
    private Long taxRateId;

    @NotNull(message = "Tax component type is required")
    private TaxComponentType componentType;

    @NotNull(message = "Component rate is required")
    @DecimalMin(
            value = "0.000000",
            inclusive = true,
            message = "Component rate cannot be negative"
    )
    @DecimalMax(
            value = "100.000000",
            inclusive = true,
            message = "Component rate cannot exceed 100"
    )
    private BigDecimal rate;

    private TaxRateComponentStatus status;

    public Long getTaxRateId() {
        return taxRateId;
    }

    public void setTaxRateId(Long taxRateId) {
        this.taxRateId = taxRateId;
    }

    public TaxComponentType getComponentType() {
        return componentType;
    }

    public void setComponentType(TaxComponentType componentType) {
        this.componentType = componentType;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public TaxRateComponentStatus getStatus() {
        return status;
    }

    public void setStatus(TaxRateComponentStatus status) {
        this.status = status;
    }
}