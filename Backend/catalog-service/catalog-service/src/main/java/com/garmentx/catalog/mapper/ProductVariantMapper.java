package com.garmentx.catalog.mapper;

import com.garmentx.catalog.dto.ProductVariantRequest;
import com.garmentx.catalog.dto.ProductVariantResponse;
import com.garmentx.catalog.entity.Product;
import com.garmentx.catalog.entity.ProductVariant;
import org.springframework.stereotype.Component;

@Component
public class ProductVariantMapper {

    public ProductVariant toEntity(
            ProductVariantRequest request,
            Product product) {

        ProductVariant variant = new ProductVariant();

        variant.setProduct(product);
        variant.setSku(request.getSku());
        variant.setBarcode(request.getBarcode());
        variant.setSize(request.getSize());
        variant.setColor(request.getColor());
        variant.setPrice(request.getPrice());
        variant.setDiscount(request.getDiscount());
        variant.setTax(request.getTax());
        variant.setActive(request.isActive());

        return variant;
    }

    public ProductVariantResponse toResponse(
            ProductVariant variant) {

        ProductVariantResponse response =
                new ProductVariantResponse();

        response.setId(variant.getId());
        response.setProductId(variant.getProduct().getId());
        response.setSku(variant.getSku());
        response.setBarcode(variant.getBarcode());
        response.setSize(variant.getSize());
        response.setColor(variant.getColor());
        response.setPrice(variant.getPrice());
        response.setDiscount(variant.getDiscount());
        response.setTax(variant.getTax());
        response.setActive(variant.isActive());
        response.setCreatedAt(variant.getCreatedAt());
        response.setUpdatedAt(variant.getUpdatedAt());

        return response;
    }
}