package com.garmentx.catalog.dto;

import com.garmentx.catalog.entity.TaxComponentType;
import com.garmentx.catalog.entity.TaxRateComponentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TaxRateComponentResponse {

    private Long id;

    private Long taxRateId;

    private String taxCategoryName;

    private String taxCategoryCode;

    private BigDecimal taxRate;

    private TaxComponentType componentType;

    private BigDecimal rate;

    private TaxRateComponentStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaxRateId() {
        return taxRateId;
    }

    public void setTaxRateId(Long taxRateId) {
        this.taxRateId = taxRateId;
    }

    public String getTaxCategoryName() {
        return taxCategoryName;
    }

    public void setTaxCategoryName(String taxCategoryName) {
        this.taxCategoryName = taxCategoryName;
    }

    public String getTaxCategoryCode() {
        return taxCategoryCode;
    }

    public void setTaxCategoryCode(String taxCategoryCode) {
        this.taxCategoryCode = taxCategoryCode;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}