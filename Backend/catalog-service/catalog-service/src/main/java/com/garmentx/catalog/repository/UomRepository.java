package com.garmentx.catalog.repository;

import com.garmentx.catalog.entity.Uom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UomRepository extends JpaRepository<Uom, Long> {

    boolean existsByNameIgnoreCase(String name);

    boolean existsByCodeIgnoreCase(String code);

    Optional<Uom> findByNameIgnoreCase(String name);

    Optional<Uom> findByCodeIgnoreCase(String code);

    boolean existsByNameIgnoreCaseAndIdNot(
            String name,
            Long id
    );

    boolean existsByCodeIgnoreCaseAndIdNot(
            String code,
            Long id
    );
}