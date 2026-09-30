package com.garmentx.catalog.controller;

import com.garmentx.catalog.dto.TaxRateRequest;
import com.garmentx.catalog.dto.TaxRateResponse;
import com.garmentx.catalog.service.TaxRateService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalog/tax-rates")
public class TaxRateController {

    private final TaxRateService taxRateService;

    public TaxRateController(TaxRateService taxRateService) {
        this.taxRateService = taxRateService;
    }

    // =========================
    // CREATE
    // =========================

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<TaxRateResponse> createTaxRate(
            @Valid @RequestBody TaxRateRequest request) {

        TaxRateResponse response =
                taxRateService.createTaxRate(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // =========================
    // GET ALL
    // =========================

    @GetMapping
    public ResponseEntity<List<TaxRateResponse>> getAllTaxRates() {

        return ResponseEntity.ok(
                taxRateService.getAllTaxRates()
        );
    }

    // =========================
    // GET BY ID
    // =========================

    @GetMapping("/{id}")
    public ResponseEntity<TaxRateResponse> getTaxRateById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                taxRateService.getTaxRateById(id)
        );
    }

    // =========================
    // GET BY TAX CATEGORY
    // =========================

    @GetMapping("/category/{taxCategoryId}")
    public ResponseEntity<List<TaxRateResponse>> getTaxRatesByCategory(
            @PathVariable Long taxCategoryId) {

        return ResponseEntity.ok(
                taxRateService.getTaxRatesByCategory(
                        taxCategoryId
                )
        );
    }

    // =========================
    // UPDATE
    // =========================

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<TaxRateResponse> updateTaxRate(
            @PathVariable Long id,
            @Valid @RequestBody TaxRateRequest request) {

        return ResponseEntity.ok(
                taxRateService.updateTaxRate(id, request)
        );
    }

    // =========================
    // DELETE
    // =========================

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> deleteTaxRate(
            @PathVariable Long id) {

        taxRateService.deleteTaxRate(id);

        return ResponseEntity.noContent().build();
    }
}