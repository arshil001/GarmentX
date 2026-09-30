package com.garmentx.catalog.repository;

import com.garmentx.catalog.entity.TaxComponentType;
import com.garmentx.catalog.entity.TaxRateComponent;
import com.garmentx.catalog.entity.TaxRateComponentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaxRateComponentRepository
        extends JpaRepository<TaxRateComponent, Long> {

    List<TaxRateComponent> findByTaxRateId(Long taxRateId);

    List<TaxRateComponent> findByTaxRateIdAndStatus(
            Long taxRateId,
            TaxRateComponentStatus status
    );

    Optional<TaxRateComponent> findByTaxRateIdAndComponentType(
            Long taxRateId,
            TaxComponentType componentType
    );

    boolean existsByTaxRateIdAndComponentType(
            Long taxRateId,
            TaxComponentType componentType
    );

    boolean existsByTaxRateIdAndComponentTypeAndIdNot(
            Long taxRateId,
            TaxComponentType componentType,
            Long id
    );
}