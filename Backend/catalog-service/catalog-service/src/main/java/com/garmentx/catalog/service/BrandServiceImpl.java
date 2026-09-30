package com.garmentx.catalog.service;

import com.garmentx.catalog.dto.BrandRequest;
import com.garmentx.catalog.dto.BrandResponse;
import com.garmentx.catalog.entity.Brand;
import com.garmentx.catalog.entity.BrandStatus;
import com.garmentx.catalog.exception.DuplicateResourceException;
import com.garmentx.catalog.exception.ResourceNotFoundException;
import com.garmentx.catalog.repository.BrandRepository;
import com.garmentx.catalog.service.BrandService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public BrandResponse createBrand(BrandRequest request) {

        String name = normalizeName(request.getName());
        String code = normalizeCode(request.getCode());

        if (brandRepository.existsByNameIgnoreCase(name)) {
            throw new DuplicateResourceException(
                    "Brand with name '" + name + "' already exists"
            );
        }

        if (brandRepository.existsByCodeIgnoreCase(code)) {
            throw new DuplicateResourceException(
                    "Brand with code '" + code + "' already exists"
            );
        }

        Brand brand = new Brand();

        brand.setName(name);
        brand.setCode(code);
        brand.setDescription(normalizeOptional(request.getDescription()));
        brand.setLogo(normalizeOptional(request.getLogo()));
        brand.setWebsite(normalizeOptional(request.getWebsite()));

        brand.setStatus(
                request.getStatus() != null
                        ? request.getStatus()
                        : BrandStatus.ACTIVE
        );

        Brand savedBrand = brandRepository.save(brand);

        return mapToResponse(savedBrand);
    }

    @Override
    @Transactional(readOnly = true)
    public BrandResponse getBrandById(Long id) {

        Brand brand = brandRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Brand with id " + id + " not found"
                        )
                );

        return mapToResponse(brand);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BrandResponse> getAllBrands() {

        return brandRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public BrandResponse updateBrand(
            Long id,
            BrandRequest request
    ) {

        Brand brand = brandRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Brand with id " + id + " not found"
                        )
                );

        String name = normalizeName(request.getName());
        String code = normalizeCode(request.getCode());

        // Duplicate name check
        if (brandRepository.existsByNameIgnoreCaseAndIdNot(name, id)) {
            throw new DuplicateResourceException(
                    "Brand with name '" + name + "' already exists"
            );
        }

        // Duplicate code check
        if (brandRepository.existsByCodeIgnoreCaseAndIdNot(code, id)) {
            throw new DuplicateResourceException(
                    "Brand with code '" + code + "' already exists"
            );
        }

        brand.setName(name);
        brand.setCode(code);

        brand.setDescription(
                normalizeOptional(request.getDescription())
        );

        brand.setLogo(
                normalizeOptional(request.getLogo())
        );

        brand.setWebsite(
                normalizeOptional(request.getWebsite())
        );

        if (request.getStatus() != null) {
            brand.setStatus(request.getStatus());
        }

        Brand updatedBrand = brandRepository.save(brand);

        return mapToResponse(updatedBrand);
    }

    @Override
    public void deleteBrand(Long id) {

        Brand brand = brandRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Brand with id " + id + " not found"
                        )
                );

        brandRepository.delete(brand);
    }

    private String normalizeName(String value) {
        return value.trim().replaceAll("\\s+", " ");
    }

    private String normalizeCode(String value) {
        return value.trim().toUpperCase();
    }

    private String normalizeOptional(String value) {

        if (value == null || value.isBlank()) {
            return null;
        }

        return value.trim();
    }

    private BrandResponse mapToResponse(Brand brand) {

        BrandResponse response = new BrandResponse();

        response.setId(brand.getId());
        response.setName(brand.getName());
        response.setCode(brand.getCode());
        response.setDescription(brand.getDescription());
        response.setLogo(brand.getLogo());
        response.setWebsite(brand.getWebsite());
        response.setStatus(brand.getStatus());
        response.setCreatedAt(brand.getCreatedAt());
        response.setUpdatedAt(brand.getUpdatedAt());

        return response;
    }
}