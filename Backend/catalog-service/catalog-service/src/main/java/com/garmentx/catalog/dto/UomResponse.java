package com.garmentx.catalog.dto;

import com.garmentx.catalog.entity.UomStatus;
import com.garmentx.catalog.entity.UomType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class UomResponse {

    private Long id;
    private String name;
    private String code;
    private UomType type;
    private BigDecimal conversionFactor;
    private UomStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}