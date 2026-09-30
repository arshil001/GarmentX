package com.garmentx.catalog.service;

import com.garmentx.catalog.dto.TaxRateRequest;
import com.garmentx.catalog.dto.TaxRateResponse;
import com.garmentx.catalog.entity.TaxCategory;
import com.garmentx.catalog.entity.TaxRate;
import com.garmentx.catalog.entity.TaxRateStatus;
import com.garmentx.catalog.exception.DuplicateResourceException;
import com.garmentx.catalog.exception.ResourceNotFoundException;
import com.garmentx.catalog.repository.TaxCategoryRepository;
import com.garmentx.catalog.repository.TaxRateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class TaxRateServiceImpl implements TaxRateService {

    private final TaxRateRepository taxRateRepository;
    private final TaxCategoryRepository taxCategoryRepository;

    public TaxRateServiceImpl(
            TaxRateRepository taxRateRepository,
            TaxCategoryRepository taxCategoryRepository) {

        this.taxRateRepository = taxRateRepository;
        this.taxCategoryRepository = taxCategoryRepository;
    }

    @Override
    public TaxRateResponse createTaxRate(TaxRateRequest request) {

        validateDates(
                request.getEffectiveFrom(),
                request.getEffectiveTo()
        );

        TaxCategory taxCategory = getTaxCategory(
                request.getTaxCategoryId()
        );

        LocalDate effectiveFrom = request.getEffectiveFrom();
        LocalDate effectiveTo = request.getEffectiveTo();

        /*
         * Database unique constraint also protects this,
         * but we provide a clean business error.
         */
        if (taxRateRepository.existsByTaxCategoryIdAndEffectiveFrom(
                taxCategory.getId(),
                effectiveFrom)) {

            throw new DuplicateResourceException(
                    "Tax rate with this effective from date already exists for the selected tax category"
            );
        }

        TaxRateStatus status = request.getStatus() != null
                ? request.getStatus()
                : TaxRateStatus.ACTIVE;

        /*
         * Only ACTIVE rates participate in overlap validation.
         */
        if (status == TaxRateStatus.ACTIVE) {

            validateNoOverlap(
                    taxCategory.getId(),
                    effectiveFrom,
                    effectiveTo
            );
        }

        TaxRate taxRate = new TaxRate();

        taxRate.setTaxCategory(taxCategory);
        taxRate.setRate(request.getRate());
        taxRate.setEffectiveFrom(effectiveFrom);
        taxRate.setEffectiveTo(effectiveTo);
        taxRate.setStatus(status);

        TaxRate savedTaxRate = taxRateRepository.save(taxRate);

        return mapToResponse(savedTaxRate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaxRateResponse> getAllTaxRates() {

        return taxRateRepository
                .findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TaxRateResponse getTaxRateById(Long id) {

        TaxRate taxRate = taxRateRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Tax rate not found with id: " + id
                        )
                );

        return mapToResponse(taxRate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaxRateResponse> getTaxRatesByCategory(
            Long taxCategoryId) {

        /*
         * Validate category first so an invalid category
         * does not silently return an empty list.
         */
        getTaxCategory(taxCategoryId);

        return taxRateRepository
                .findByTaxCategoryIdOrderByEffectiveFromDesc(
                        taxCategoryId
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public TaxRateResponse updateTaxRate(
            Long id,
            TaxRateRequest request) {

        validateDates(
                request.getEffectiveFrom(),
                request.getEffectiveTo()
        );

        TaxRate taxRate = taxRateRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Tax rate not found with id: " + id
                        )
                );

        TaxCategory taxCategory = getTaxCategory(
                request.getTaxCategoryId()
        );

        LocalDate effectiveFrom = request.getEffectiveFrom();
        LocalDate effectiveTo = request.getEffectiveTo();

        if (taxRateRepository
                .existsByTaxCategoryIdAndEffectiveFromAndIdNot(
                        taxCategory.getId(),
                        effectiveFrom,
                        id)) {

            throw new DuplicateResourceException(
                    "Tax rate with this effective from date already exists for the selected tax category"
            );
        }

        TaxRateStatus status = request.getStatus() != null
                ? request.getStatus()
                : taxRate.getStatus();

        if (status == TaxRateStatus.ACTIVE) {

            validateNoOverlapExcludingId(
                    taxCategory.getId(),
                    effectiveFrom,
                    effectiveTo,
                    id
            );
        }

        taxRate.setTaxCategory(taxCategory);
        taxRate.setRate(request.getRate());
        taxRate.setEffectiveFrom(effectiveFrom);
        taxRate.setEffectiveTo(effectiveTo);
        taxRate.setStatus(status);

        TaxRate updatedTaxRate = taxRateRepository.save(taxRate);

        return mapToResponse(updatedTaxRate);
    }

    @Override
    public void deleteTaxRate(Long id) {

        TaxRate taxRate = taxRateRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Tax rate not found with id: " + id
                        )
                );

        taxRateRepository.delete(taxRate);
    }

    private TaxCategory getTaxCategory(Long taxCategoryId) {

        return taxCategoryRepository
                .findById(taxCategoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Tax category not found with id: "
                                        + taxCategoryId
                        )
                );
    }

    private void validateDates(
            LocalDate effectiveFrom,
            LocalDate effectiveTo) {

        if (effectiveFrom == null) {
            throw new IllegalArgumentException(
                    "Effective from date is required"
            );
        }

        if (effectiveTo != null
                && effectiveTo.isBefore(effectiveFrom)) {

            throw new IllegalArgumentException(
                    "Effective to date cannot be before effective from date"
            );
        }
    }

    private void validateNoOverlap(
            Long taxCategoryId,
            LocalDate effectiveFrom,
            LocalDate effectiveTo) {

        boolean overlap;

        if (effectiveTo == null) {

            overlap = taxRateRepository
                    .existsOverlappingActiveOpenEndedRate(
                            taxCategoryId,
                            effectiveFrom
                    );

        } else {

            overlap = taxRateRepository
                    .existsOverlappingActiveRate(
                            taxCategoryId,
                            effectiveFrom,
                            effectiveTo
                    );
        }

        if (overlap) {

            throw new DuplicateResourceException(
                    "An active tax rate already exists for the selected tax category and effective date range"
            );
        }
    }

    private void validateNoOverlapExcludingId(
            Long taxCategoryId,
            LocalDate effectiveFrom,
            LocalDate effectiveTo,
            Long id) {

        boolean overlap;

        if (effectiveTo == null) {

            overlap = taxRateRepository
                    .existsOverlappingActiveOpenEndedRateExcludingId(
                            taxCategoryId,
                            effectiveFrom,
                            id
                    );

        } else {

            overlap = taxRateRepository
                    .existsOverlappingActiveRateExcludingId(
                            taxCategoryId,
                            effectiveFrom,
                            effectiveTo,
                            id
                    );
        }

        if (overlap) {

            throw new DuplicateResourceException(
                    "An active tax rate already exists for the selected tax category and effective date range"
            );
        }
    }

    private TaxRateResponse mapToResponse(TaxRate taxRate) {

        TaxRateResponse response = new TaxRateResponse();

        response.setId(taxRate.getId());

        response.setTaxCategoryId(
                taxRate.getTaxCategory().getId()
        );

        response.setTaxCategoryName(
                taxRate.getTaxCategory().getName()
        );

        response.setTaxCategoryCode(
                taxRate.getTaxCategory().getCode()
        );

        response.setRate(taxRate.getRate());

        response.setEffectiveFrom(
                taxRate.getEffectiveFrom()
        );

        response.setEffectiveTo(
                taxRate.getEffectiveTo()
        );

        response.setStatus(
                taxRate.getStatus()
        );

        response.setCreatedAt(
                taxRate.getCreatedAt()
        );

        response.setUpdatedAt(
                taxRate.getUpdatedAt()
        );

        return response;
    }
}