package com.garmentx.catalog.controller;

import com.garmentx.catalog.dto.TaxCategoryRequest;
import com.garmentx.catalog.dto.TaxCategoryResponse;
import com.garmentx.catalog.service.TaxCategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalog/tax-categories")
public class TaxCategoryController {

    private final TaxCategoryService taxCategoryService;

    public TaxCategoryController(
            TaxCategoryService taxCategoryService) {

        this.taxCategoryService = taxCategoryService;
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<TaxCategoryResponse> createTaxCategory(
            @Valid @RequestBody TaxCategoryRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taxCategoryService.createTaxCategory(request));
    }

    @GetMapping
    public ResponseEntity<List<TaxCategoryResponse>>
    getAllTaxCategories() {

        return ResponseEntity.ok(
                taxCategoryService.getAllTaxCategories()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaxCategoryResponse>
    getTaxCategoryById(@PathVariable Long id) {

        return ResponseEntity.ok(
                taxCategoryService.getTaxCategoryById(id)
        );
    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{id}")
    public ResponseEntity<TaxCategoryResponse>
    updateTaxCategory(
            @PathVariable Long id,
            @Valid @RequestBody TaxCategoryRequest request) {

        return ResponseEntity.ok(
                taxCategoryService.updateTaxCategory(id, request)
        );
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaxCategory(
            @PathVariable Long id) {

        taxCategoryService.deleteTaxCategory(id);

        return ResponseEntity.noContent().build();
    }
}