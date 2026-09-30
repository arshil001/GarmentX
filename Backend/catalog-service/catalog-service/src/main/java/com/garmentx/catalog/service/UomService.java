package com.garmentx.catalog.service;

import com.garmentx.catalog.dto.UomRequest;
import com.garmentx.catalog.dto.UomResponse;

import java.util.List;

public interface UomService {

    UomResponse createUom(UomRequest request);

    UomResponse getUomById(Long id);

    List<UomResponse> getAllUoms();

    UomResponse updateUom(Long id, UomRequest request);

    void deleteUom(Long id);
}