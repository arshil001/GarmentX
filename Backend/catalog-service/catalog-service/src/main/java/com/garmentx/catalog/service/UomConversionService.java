package com.garmentx.catalog.service;

import com.garmentx.catalog.dto.UomConversionRequest;
import com.garmentx.catalog.dto.UomConversionResponse;

import java.util.List;

public interface UomConversionService {

    UomConversionResponse createConversion(
            UomConversionRequest request
    );

    UomConversionResponse getConversionById(Long id);

    List<UomConversionResponse> getAllConversions();

    UomConversionResponse updateConversion(
            Long id,
            UomConversionRequest request
    );

    void deleteConversion(Long id);

    List<UomConversionResponse> getConversionsFromUom(
            Long fromUomId
    );

    List<UomConversionResponse> getConversionsToUom(
            Long toUomId
    );
}