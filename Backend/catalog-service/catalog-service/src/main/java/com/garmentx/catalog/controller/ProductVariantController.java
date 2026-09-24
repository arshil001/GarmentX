package com.garmentx.catalog.controller;

import com.garmentx.catalog.dto.ProductVariantRequest;
import com.garmentx.catalog.dto.ProductVariantResponse;
import com.garmentx.catalog.service.ProductVariantService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalog/variants")
public class ProductVariantController {

    private final ProductVariantService productVariantService;

    public ProductVariantController(
            ProductVariantService productVariantService) {

        this.productVariantService = productVariantService;
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<ProductVariantResponse> createVariant(
            @Valid @RequestBody ProductVariantRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productVariantService.createVariant(request));
    }

    @GetMapping
    public ResponseEntity<List<ProductVariantResponse>> getAllVariants() {

        return ResponseEntity.ok(
                productVariantService.getAllVariants()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductVariantResponse> getVariantById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                productVariantService.getVariantById(id)
        );
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductVariantResponse>> getVariantsByProduct(
            @PathVariable Long productId) {

        return ResponseEntity.ok(
                productVariantService.getVariantsByProductId(productId)
        );
    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{id}")
    public ResponseEntity<ProductVariantResponse> updateVariant(
            @PathVariable Long id,
            @Valid @RequestBody ProductVariantRequest request) {

        return ResponseEntity.ok(
                productVariantService.updateVariant(id, request)
        );
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVariant(
            @PathVariable Long id) {

        productVariantService.deleteVariant(id);

        return ResponseEntity.ok(
                "Product variant deleted successfully"
        );
    }
}