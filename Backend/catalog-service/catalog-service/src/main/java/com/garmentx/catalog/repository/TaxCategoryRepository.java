package com.garmentx.catalog.repository;

import com.garmentx.catalog.entity.TaxCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface TaxCategoryRepository
        extends JpaRepository<TaxCategory, Long> {

    boolean existsByNameIgnoreCase(String name);

    boolean existsByCodeIgnoreCase(String code);

    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);

    boolean existsByCodeIgnoreCaseAndIdNot(String code, Long id);

    Optional<TaxCategory> findByNameIgnoreCase(String name);

    Optional<TaxCategory> findByCodeIgnoreCase(String code);
}