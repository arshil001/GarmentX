package com.garmentx.catalog.dto;

import com.garmentx.catalog.entity.UomConversionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class UomConversionResponse {

    private Long id;

    private Long fromUomId;
    private String fromUomName;
    private String fromUomCode;

    private Long toUomId;
    private String toUomName;
    private String toUomCode;

    private BigDecimal conversionFactor;

    private UomConversionStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFromUomId() {
        return fromUomId;
    }

    public void setFromUomId(Long fromUomId) {
        this.fromUomId = fromUomId;
    }

    public String getFromUomName() {
        return fromUomName;
    }

    public void setFromUomName(String fromUomName) {
        this.fromUomName = fromUomName;
    }

    public String getFromUomCode() {
        return fromUomCode;
    }

    public void setFromUomCode(String fromUomCode) {
        this.fromUomCode = fromUomCode;
    }

    public Long getToUomId() {
        return toUomId;
    }

    public void setToUomId(Long toUomId) {
        this.toUomId = toUomId;
    }

    public String getToUomName() {
        return toUomName;
    }

    public void setToUomName(String toUomName) {
        this.toUomName = toUomName;
    }

    public String getToUomCode() {
        return toUomCode;
    }

    public void setToUomCode(String toUomCode) {
        this.toUomCode = toUomCode;
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