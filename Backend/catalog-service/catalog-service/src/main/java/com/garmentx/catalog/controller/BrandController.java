package com.garmentx.catalog.controller;

import com.garmentx.catalog.dto.BrandRequest;
import com.garmentx.catalog.dto.BrandResponse;
import com.garmentx.catalog.service.BrandService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalog/brands")
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<BrandResponse> createBrand(
            @Valid @RequestBody BrandRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(brandService.createBrand(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrandResponse> getBrandById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                brandService.getBrandById(id)
        );
    }

    @GetMapping
    public ResponseEntity<List<BrandResponse>> getAllBrands() {

        return ResponseEntity.ok(
                brandService.getAllBrands()
        );
    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{id}")
    public ResponseEntity<BrandResponse> updateBrand(
            @PathVariable Long id,
            @Valid @RequestBody BrandRequest request
    ) {

        return ResponseEntity.ok(
                brandService.updateBrand(id, request)
        );
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrand(
            @PathVariable Long id
    ) {

        brandService.deleteBrand(id);

        return ResponseEntity.noContent().build();
    }
}