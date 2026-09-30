package com.garmentx.catalog.dto;

import com.garmentx.catalog.entity.UomStatus;
import com.garmentx.catalog.entity.UomType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class UomRequest {

    @NotBlank(message = "UOM name is required")
    @Size(max = 100, message = "UOM name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "UOM code is required")
    @Size(max = 30, message = "UOM code must not exceed 30 characters")
    @Pattern(
            regexp = "^[A-Za-z0-9_-]+$",
            message = "UOM code can contain only letters, numbers, hyphen and underscore"
    )
    private String code;

    @NotNull(message = "UOM type is required")
    private UomType type;

    @NotNull(message = "Conversion factor is required")
    @DecimalMin(
            value = "0.000001",
            message = "Conversion factor must be greater than 0"
    )
    private BigDecimal conversionFactor;

    private UomStatus status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public UomType getType() {
        return type;
    }

    public void setType(UomType type) {
        this.type = type;
    }

    public BigDecimal getConversionFactor() {
        return conversionFactor;
    }

    public void setConversionFactor(BigDecimal conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public UomStatus getStatus() {
        return status;
    }

    public void setStatus(UomStatus status) {
        this.status = status;
    }
}