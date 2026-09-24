package com.garmentx.catalog.repository;

import com.garmentx.catalog.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductVariantRepository
        extends JpaRepository<ProductVariant, Long> {

    Optional<ProductVariant> findBySku(String sku);

    boolean existsBySku(String sku);
}