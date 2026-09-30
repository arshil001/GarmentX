package com.garmentx.catalog.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "tax_rate_components",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_tax_rate_component_type",
                        columnNames = {
                                "tax_rate_id",
                                "component_type"
                        }
                )
        }
)
public class TaxRateComponent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "tax_rate_id",
            nullable = false
    )
    private TaxRate taxRate;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "component_type",
            nullable = false,
            length = 20
    )
    private TaxComponentType componentType;

    @Column(
            nullable = false,
            precision = 19,
            scale = 6
    )
    private BigDecimal rate;

    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 20
    )
    private TaxRateComponentStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {

        LocalDateTime now = LocalDateTime.now();

        createdAt = now;
        updatedAt = now;

        if (status == null) {
            status = TaxRateComponentStatus.ACTIVE;
        }
    }

    @PreUpdate
    protected void onUpdate() {

        updatedAt = LocalDateTime.now();
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public TaxRate getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(TaxRate taxRate) {
        this.taxRate = taxRate;
    }

    public void setId(Long id) {
        this.id = id;
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
}