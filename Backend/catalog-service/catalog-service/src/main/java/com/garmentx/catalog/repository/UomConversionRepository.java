package com.garmentx.catalog.repository;

import com.garmentx.catalog.entity.UomConversion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UomConversionRepository
        extends JpaRepository<UomConversion, Long> {

    boolean existsByFromUomIdAndToUomId(
            Long fromUomId,
            Long toUomId
    );

    boolean existsByFromUomIdAndToUomIdAndIdNot(
            Long fromUomId,
            Long toUomId,
            Long id
    );

    List<UomConversion> findByFromUomId(Long fromUomId);

    List<UomConversion> findByToUomId(Long toUomId);
}