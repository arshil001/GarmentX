package com.garmentx.catalog.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "uoms",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_uom_name",
                        columnNames = "name"
                ),
                @UniqueConstraint(
                        name = "uk_uom_code",
                        columnNames = "code"
                )
        }
)
public class Uom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 30)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private UomType type;

    @Column(nullable = false, precision = 19, scale = 6)
    private BigDecimal conversionFactor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UomStatus status;

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
            status = UomStatus.ACTIVE;
        }

        if (conversionFactor == null) {
            conversionFactor = BigDecimal.ONE;
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}