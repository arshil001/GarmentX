package com.garmentx.catalog.repository;

import com.garmentx.catalog.entity.TaxRate;
import com.garmentx.catalog.entity.TaxRateStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TaxRateRepository
        extends JpaRepository<TaxRate, Long> {

    List<TaxRate> findByTaxCategoryId(Long taxCategoryId);

    List<TaxRate> findByTaxCategoryIdOrderByEffectiveFromDesc(
            Long taxCategoryId
    );

    List<TaxRate> findByStatus(TaxRateStatus status);

    boolean existsByTaxCategoryIdAndEffectiveFrom(
            Long taxCategoryId,
            LocalDate effectiveFrom
    );

    boolean existsByTaxCategoryIdAndEffectiveFromAndIdNot(
            Long taxCategoryId,
            LocalDate effectiveFrom,
            Long id
    );

    /*
     * Used when requested effectiveTo is NOT null.
     *
     * Two date ranges overlap when:
     *
     * existing.from <= requested.to
     * AND
     * existing.to >= requested.from
     *
     * NULL existing.to means the existing rate has no end date.
     */
    @Query("""
            SELECT COUNT(tr) > 0
            FROM TaxRate tr
            WHERE tr.taxCategory.id = :taxCategoryId
              AND tr.status = com.garmentx.catalog.entity.TaxRateStatus.ACTIVE
              AND tr.effectiveFrom <= :effectiveTo
              AND (
                    tr.effectiveTo IS NULL
                    OR tr.effectiveTo >= :effectiveFrom
                  )
            """)
    boolean existsOverlappingActiveRate(
            @Param("taxCategoryId") Long taxCategoryId,
            @Param("effectiveFrom") LocalDate effectiveFrom,
            @Param("effectiveTo") LocalDate effectiveTo
    );

    @Query("""
            SELECT COUNT(tr) > 0
            FROM TaxRate tr
            WHERE tr.taxCategory.id = :taxCategoryId
              AND tr.id <> :id
              AND tr.status = com.garmentx.catalog.entity.TaxRateStatus.ACTIVE
              AND tr.effectiveFrom <= :effectiveTo
              AND (
                    tr.effectiveTo IS NULL
                    OR tr.effectiveTo >= :effectiveFrom
                  )
            """)
    boolean existsOverlappingActiveRateExcludingId(
            @Param("taxCategoryId") Long taxCategoryId,
            @Param("effectiveFrom") LocalDate effectiveFrom,
            @Param("effectiveTo") LocalDate effectiveTo,
            @Param("id") Long id
    );

    /*
     * Used when requested effectiveTo is NULL.
     *
     * Requested range:
     *
     * effectiveFrom -> infinity
     *
     * Any active existing rate whose end date is
     * NULL or >= requested effectiveFrom overlaps.
     */
    @Query("""
            SELECT COUNT(tr) > 0
            FROM TaxRate tr
            WHERE tr.taxCategory.id = :taxCategoryId
              AND tr.status = com.garmentx.catalog.entity.TaxRateStatus.ACTIVE
              AND (
                    tr.effectiveTo IS NULL
                    OR tr.effectiveTo >= :effectiveFrom
                  )
            """)
    boolean existsOverlappingActiveOpenEndedRate(
            @Param("taxCategoryId") Long taxCategoryId,
            @Param("effectiveFrom") LocalDate effectiveFrom
    );

    @Query("""
            SELECT COUNT(tr) > 0
            FROM TaxRate tr
            WHERE tr.taxCategory.id = :taxCategoryId
              AND tr.id <> :id
              AND tr.status = com.garmentx.catalog.entity.TaxRateStatus.ACTIVE
              AND (
                    tr.effectiveTo IS NULL
                    OR tr.effectiveTo >= :effectiveFrom
                  )
            """)
    boolean existsOverlappingActiveOpenEndedRateExcludingId(
            @Param("taxCategoryId") Long taxCategoryId,
            @Param("effectiveFrom") LocalDate effectiveFrom,
            @Param("id") Long id
    );
}