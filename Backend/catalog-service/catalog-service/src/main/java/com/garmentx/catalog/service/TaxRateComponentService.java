package com.garmentx.catalog.service;

import com.garmentx.catalog.dto.TaxRateComponentRequest;
import com.garmentx.catalog.dto.TaxRateComponentResponse;

import java.util.List;

public interface TaxRateComponentService {

    TaxRateComponentResponse createComponent(
            TaxRateComponentRequest request
    );

    List<TaxRateComponentResponse> getAllComponents();

    TaxRateComponentResponse getComponentById(Long id);

    List<TaxRateComponentResponse> getComponentsByTaxRate(
            Long taxRateId
    );

    TaxRateComponentResponse updateComponent(
            Long id,
            TaxRateComponentRequest request
    );

    void deleteComponent(Long id);
}