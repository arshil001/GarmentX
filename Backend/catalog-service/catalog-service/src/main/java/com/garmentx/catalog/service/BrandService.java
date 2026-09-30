package com.garmentx.catalog.service;

import com.garmentx.catalog.dto.BrandRequest;
import com.garmentx.catalog.dto.BrandResponse;

import java.util.List;

public interface BrandService {

    BrandResponse createBrand(BrandRequest request);

    BrandResponse getBrandById(Long id);

    List<BrandResponse> getAllBrands();

    BrandResponse updateBrand(Long id, BrandRequest request);

    void deleteBrand(Long id);
}