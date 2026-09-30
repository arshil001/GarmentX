package com.garmentx.catalog.service;

import com.garmentx.catalog.dto.TaxCategoryRequest;
import com.garmentx.catalog.dto.TaxCategoryResponse;

import java.util.List;

public interface TaxCategoryService {

    TaxCategoryResponse createTaxCategory(TaxCategoryRequest request);

    List<TaxCategoryResponse> getAllTaxCategories();

    TaxCategoryResponse getTaxCategoryById(Long id);

    TaxCategoryResponse updateTaxCategory(
            Long id,
            TaxCategoryRequest request
    );

    void deleteTaxCategory(Long id);
}