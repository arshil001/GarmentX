package com.garmentx.catalog.service;

import com.garmentx.catalog.dto.ProductVariantRequest;
import com.garmentx.catalog.dto.ProductVariantResponse;
import com.garmentx.catalog.entity.Product;
import com.garmentx.catalog.entity.ProductVariant;
import com.garmentx.catalog.exception.ResourceNotFoundException;
import com.garmentx.catalog.mapper.ProductVariantMapper;
import com.garmentx.catalog.repository.ProductRepository;
import com.garmentx.catalog.repository.ProductVariantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductVariantService {

    private final ProductVariantRepository productVariantRepository;
    private final ProductRepository productRepository;
    private final ProductVariantMapper productVariantMapper;

    public ProductVariantService(
            ProductVariantRepository productVariantRepository,
            ProductRepository productRepository,
            ProductVariantMapper productVariantMapper) {

        this.productVariantRepository = productVariantRepository;
        this.productRepository = productRepository;
        this.productVariantMapper = productVariantMapper;
    }

    public ProductVariantResponse createVariant(
            ProductVariantRequest request) {

        // Check whether product exists
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id: "
                                        + request.getProductId()
                        )
                );

        // Check duplicate SKU
        if (productVariantRepository.existsBySku(request.getSku())) {
            throw new IllegalArgumentException(
                    "Variant already exists with SKU: "
                            + request.getSku()
            );
        }

        ProductVariant variant =
                productVariantMapper.toEntity(request, product);

        ProductVariant savedVariant =
                productVariantRepository.save(variant);

        return productVariantMapper.toResponse(savedVariant);
    }

    public List<ProductVariantResponse> getAllVariants() {

        return productVariantRepository.findAll()
                .stream()
                .map(productVariantMapper::toResponse)
                .toList();
    }

    public ProductVariantResponse getVariantById(Long id) {

        ProductVariant variant =
                productVariantRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Product variant not found with id: "
                                                + id
                                )
                        );

        return productVariantMapper.toResponse(variant);
    }

    public List<ProductVariantResponse> getVariantsByProductId(
            Long productId) {

        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException(
                    "Product not found with id: " + productId
            );
        }

        return productVariantRepository.findAll()
                .stream()
                .filter(variant ->
                        variant.getProduct().getId().equals(productId))
                .map(productVariantMapper::toResponse)
                .toList();
    }

    public ProductVariantResponse updateVariant(
            Long id,
            ProductVariantRequest request) {

        ProductVariant existingVariant =
                productVariantRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Product variant not found with id: "
                                                + id
                                )
                        );

        Product product =
                productRepository.findById(request.getProductId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Product not found with id: "
                                                + request.getProductId()
                                )
                        );

        // Check SKU only if it is changed
        if (!existingVariant.getSku().equals(request.getSku())
                && productVariantRepository.existsBySku(request.getSku())) {

            throw new IllegalArgumentException(
                    "Variant already exists with SKU: "
                            + request.getSku()
            );
        }

        existingVariant.setProduct(product);
        existingVariant.setSku(request.getSku());
        existingVariant.setBarcode(request.getBarcode());
        existingVariant.setSize(request.getSize());
        existingVariant.setColor(request.getColor());
        existingVariant.setPrice(request.getPrice());
        existingVariant.setDiscount(request.getDiscount());
        existingVariant.setTax(request.getTax());
        existingVariant.setActive(request.isActive());

        ProductVariant updatedVariant =
                productVariantRepository.save(existingVariant);

        return productVariantMapper.toResponse(updatedVariant);
    }

    public void deleteVariant(Long id) {

        if (!productVariantRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Product variant not found with id: " + id
            );
        }

        productVariantRepository.deleteById(id);
    }
}