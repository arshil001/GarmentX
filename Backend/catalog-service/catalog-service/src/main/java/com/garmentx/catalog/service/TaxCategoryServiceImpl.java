package com.garmentx.catalog.service;

import com.garmentx.catalog.dto.TaxCategoryRequest;
import com.garmentx.catalog.dto.TaxCategoryResponse;
import com.garmentx.catalog.entity.TaxCategory;
import com.garmentx.catalog.entity.TaxStatus;
import com.garmentx.catalog.exception.DuplicateResourceException;
import com.garmentx.catalog.exception.ResourceNotFoundException;
import com.garmentx.catalog.repository.TaxCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaxCategoryServiceImpl implements TaxCategoryService {

    private final TaxCategoryRepository taxCategoryRepository;

    public TaxCategoryServiceImpl(
            TaxCategoryRepository taxCategoryRepository) {
        this.taxCategoryRepository = taxCategoryRepository;
    }

    @Override
    public TaxCategoryResponse createTaxCategory(
            TaxCategoryRequest request) {

        String name = normalizeName(request.getName());
        String code = normalizeCode(request.getCode());

        if (taxCategoryRepository.existsByNameIgnoreCase(name)) {
            throw new DuplicateResourceException(
                    "Tax category with name '" + name + "' already exists"
            );
        }

        if (taxCategoryRepository.existsByCodeIgnoreCase(code)) {
            throw new DuplicateResourceException(
                    "Tax category with code '" + code + "' already exists"
            );
        }

        TaxCategory taxCategory = new TaxCategory();

        taxCategory.setName(name);
        taxCategory.setCode(code);
        taxCategory.setDescription(
                normalizeDescription(request.getDescription())
        );

        taxCategory.setStatus(
                request.getStatus() != null
                        ? request.getStatus()
                        : TaxStatus.ACTIVE
        );

        TaxCategory saved =
                taxCategoryRepository.save(taxCategory);

        return mapToResponse(saved);
    }

    @Override
    public List<TaxCategoryResponse> getAllTaxCategories() {

        return taxCategoryRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public TaxCategoryResponse getTaxCategoryById(Long id) {

        TaxCategory taxCategory =
                taxCategoryRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Tax category not found with id: " + id
                                )
                        );

        return mapToResponse(taxCategory);
    }

    @Override
    public TaxCategoryResponse updateTaxCategory(
            Long id,
            TaxCategoryRequest request) {

        TaxCategory taxCategory =
                taxCategoryRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Tax category not found with id: " + id
                                )
                        );

        String name = normalizeName(request.getName());
        String code = normalizeCode(request.getCode());

        if (taxCategoryRepository
                .existsByNameIgnoreCaseAndIdNot(name, id)) {

            throw new DuplicateResourceException(
                    "Tax category with name '" + name + "' already exists"
            );
        }

        if (taxCategoryRepository
                .existsByCodeIgnoreCaseAndIdNot(code, id)) {

            throw new DuplicateResourceException(
                    "Tax category with code '" + code + "' already exists"
            );
        }

        taxCategory.setName(name);
        taxCategory.setCode(code);
        taxCategory.setDescription(
                normalizeDescription(request.getDescription())
        );

        if (request.getStatus() != null) {
            taxCategory.setStatus(request.getStatus());
        }

        TaxCategory updated =
                taxCategoryRepository.save(taxCategory);

        return mapToResponse(updated);
    }

    @Override
    public void deleteTaxCategory(Long id) {

        TaxCategory taxCategory =
                taxCategoryRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Tax category not found with id: " + id
                                )
                        );

        taxCategoryRepository.delete(taxCategory);
    }

    private String normalizeName(String value) {

        return value.trim().replaceAll("\\s+", " ");
    }

    private String normalizeCode(String value) {

        return value.trim().toUpperCase();
    }

    private String normalizeDescription(String value) {

        if (value == null) {
            return null;
        }

        String description =
                value.trim().replaceAll("\\s+", " ");

        return description.isBlank()
                ? null
                : description;
    }

    private TaxCategoryResponse mapToResponse(
            TaxCategory taxCategory) {

        TaxCategoryResponse response =
                new TaxCategoryResponse();

        response.setId(taxCategory.getId());
        response.setName(taxCategory.getName());
        response.setCode(taxCategory.getCode());
        response.setDescription(taxCategory.getDescription());
        response.setStatus(taxCategory.getStatus());
        response.setCreatedAt(taxCategory.getCreatedAt());
        response.setUpdatedAt(taxCategory.getUpdatedAt());

        return response;
    }
}