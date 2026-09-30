package com.garmentx.catalog.service;

import com.garmentx.catalog.dto.TaxRateRequest;
import com.garmentx.catalog.dto.TaxRateResponse;

import java.util.List;

public interface TaxRateService {

    TaxRateResponse createTaxRate(TaxRateRequest request);

    List<TaxRateResponse> getAllTaxRates();

    TaxRateResponse getTaxRateById(Long id);

    List<TaxRateResponse> getTaxRatesByCategory(Long taxCategoryId);

    TaxRateResponse updateTaxRate(Long id, TaxRateRequest request);

    void deleteTaxRate(Long id);
}