package com.garmentx.catalog.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "uom_conversions",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_uom_conversion_pair",
                        columnNames = {
                                "from_uom_id",
                                "to_uom_id"
                        }
                )
        }
)
public class UomConversion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "from_uom_id",
            nullable = false
    )
    private Uom fromUom;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "to_uom_id",
            nullable = false
    )
    private Uom toUom;

    @Column(
            nullable = false,
            precision = 19,
            scale = 6
    )
    private BigDecimal conversionFactor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UomConversionStatus status;

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
            status = UomConversionStatus.ACTIVE;
        }
    }

    @PreUpdate
    protected void onUpdate() {

        updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Uom getFromUom() {
        return fromUom;
    }

    public void setFromUom(Uom fromUom) {
        this.fromUom = fromUom;
    }

    public Uom getToUom() {
        return toUom;
    }

    public void setToUom(Uom toUom) {
        this.toUom = toUom;
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}