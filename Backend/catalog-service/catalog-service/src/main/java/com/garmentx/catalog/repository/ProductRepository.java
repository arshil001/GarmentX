package com.garmentx.catalog.repository;

import com.garmentx.catalog.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByProductCode(String productCode);

    boolean existsByProductCode(String productCode);
}