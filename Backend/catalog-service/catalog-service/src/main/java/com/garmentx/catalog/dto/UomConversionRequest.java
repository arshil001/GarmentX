package com.garmentx.catalog.dto;

import com.garmentx.catalog.entity.UomConversionStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class UomConversionRequest {

    @NotNull(message = "From UOM ID is required")
    private Long fromUomId;

    @NotNull(message = "To UOM ID is required")
    private Long toUomId;

    @NotNull(message = "Conversion factor is required")
    @DecimalMin(
            value = "0.000001",
            message = "Conversion factor must be greater than 0"
    )
    private BigDecimal conversionFactor;

    private UomConversionStatus status;

    public Long getFromUomId() {
        return fromUomId;
    }

    public void setFromUomId(Long fromUomId) {
        this.fromUomId = fromUomId;
    }

    public Long getToUomId() {
        return toUomId;
    }

    public void setToUomId(Long toUomId) {
        this.toUomId = toUomId;
    }

    public BigDecimal getConversionFactor() {
        return conversionFactor;
    }

    public void setConversionFactor(BigDecimal conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public UomConversionStatus getStatus() {
        return status;
    }

    public void setStatus(UomConversionStatus status) {
        this.status = status;
    }
}