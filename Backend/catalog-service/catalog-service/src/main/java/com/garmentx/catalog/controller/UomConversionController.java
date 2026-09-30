package com.garmentx.catalog.controller;

import com.garmentx.catalog.dto.UomConversionRequest;
import com.garmentx.catalog.dto.UomConversionResponse;
import com.garmentx.catalog.service.UomConversionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalog/uom-conversions")
public class UomConversionController {

    private final UomConversionService conversionService;

    public UomConversionController(
            UomConversionService conversionService
    ) {
        this.conversionService = conversionService;
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<UomConversionResponse> createConversion(
            @Valid @RequestBody UomConversionRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        conversionService.createConversion(request)
                );
    }

    @GetMapping
    public ResponseEntity<List<UomConversionResponse>>
    getAllConversions() {

        return ResponseEntity.ok(
                conversionService.getAllConversions()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UomConversionResponse> getConversionById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                conversionService.getConversionById(id)
        );
    }

    @GetMapping("/from/{uomId}")
    public ResponseEntity<List<UomConversionResponse>>
    getConversionsFromUom(
            @PathVariable Long uomId
    ) {

        return ResponseEntity.ok(
                conversionService.getConversionsFromUom(uomId)
        );
    }

    @GetMapping("/to/{uomId}")
    public ResponseEntity<List<UomConversionResponse>>
    getConversionsToUom(
            @PathVariable Long uomId
    ) {

        return ResponseEntity.ok(
                conversionService.getConversionsToUom(uomId)
        );
    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{id}")
    public ResponseEntity<UomConversionResponse>
    updateConversion(
            @PathVariable Long id,
            @Valid @RequestBody UomConversionRequest request
    ) {

        return ResponseEntity.ok(
                conversionService.updateConversion(
                        id,
                        request
                )
        );
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConversion(
            @PathVariable Long id
    ) {

        conversionService.deleteConversion(id);

        return ResponseEntity.noContent().build();
    }
}