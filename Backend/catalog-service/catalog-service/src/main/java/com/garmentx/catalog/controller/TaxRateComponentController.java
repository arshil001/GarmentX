package com.garmentx.catalog.controller;

import com.garmentx.catalog.dto.TaxRateComponentRequest;
import com.garmentx.catalog.dto.TaxRateComponentResponse;
import com.garmentx.catalog.service.TaxRateComponentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalog/tax-rate-components")
public class TaxRateComponentController {

    private final TaxRateComponentService componentService;

    public TaxRateComponentController(
            TaxRateComponentService componentService) {

        this.componentService = componentService;
    }

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<TaxRateComponentResponse> createComponent(
            @Valid @RequestBody TaxRateComponentRequest request) {

        TaxRateComponentResponse response =
                componentService.createComponent(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<TaxRateComponentResponse>>
    getAllComponents() {

        return ResponseEntity.ok(
                componentService.getAllComponents()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaxRateComponentResponse>
    getComponentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                componentService.getComponentById(id)
        );
    }

    @GetMapping("/tax-rate/{taxRateId}")
    public ResponseEntity<List<TaxRateComponentResponse>>
    getComponentsByTaxRate(
            @PathVariable Long taxRateId) {

        return ResponseEntity.ok(
                componentService.getComponentsByTaxRate(taxRateId)
        );
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<TaxRateComponentResponse>
    updateComponent(
            @PathVariable Long id,
            @Valid @RequestBody TaxRateComponentRequest request) {

        return ResponseEntity.ok(
                componentService.updateComponent(
                        id,
                        request
                )
        );
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> deleteComponent(
            @PathVariable Long id) {

        componentService.deleteComponent(id);

        return ResponseEntity.noContent().build();
    }
}