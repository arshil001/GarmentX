package com.garmentx.catalog.controller;

import com.garmentx.catalog.dto.UomRequest;
import com.garmentx.catalog.dto.UomResponse;
import com.garmentx.catalog.service.UomService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalog/uoms")
public class UomController {

    private final UomService uomService;

    public UomController(UomService uomService) {
        this.uomService = uomService;
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<UomResponse> createUom(
            @Valid @RequestBody UomRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(uomService.createUom(request));
    }

    @GetMapping
    public ResponseEntity<List<UomResponse>> getAllUoms() {

        return ResponseEntity.ok(
                uomService.getAllUoms()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UomResponse> getUomById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                uomService.getUomById(id)
        );
    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{id}")
    public ResponseEntity<UomResponse> updateUom(
            @PathVariable Long id,
            @Valid @RequestBody UomRequest request
    ) {

        return ResponseEntity.ok(
                uomService.updateUom(id, request)
        );
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUom(
            @PathVariable Long id
    ) {

        uomService.deleteUom(id);

        return ResponseEntity.noContent().build();
    }
}