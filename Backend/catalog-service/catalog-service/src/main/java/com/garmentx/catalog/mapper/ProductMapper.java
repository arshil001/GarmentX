package com.garmentx.catalog.mapper;

import com.garmentx.catalog.dto.ProductRequest;
import com.garmentx.catalog.dto.ProductResponse;
import com.garmentx.catalog.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequest request) {

        Product product = new Product();

        product.setName(request.getName());
        product.setProductCode(request.getProductCode());
        product.setDescription(request.getDescription());
        product.setCategory(request.getCategory());
        product.setBrand(request.getBrand());
        product.setGender(request.getGender());
        product.setFabric(request.getFabric());
        product.setStyle(request.getStyle());
        product.setSeason(request.getSeason());
        product.setActive(request.isActive());

        return product;
    }

    public ProductResponse toResponse(Product product) {

        ProductResponse response = new ProductResponse();

        response.setId(product.getId());
        response.setName(product.getName());
        response.setProductCode(product.getProductCode());
        response.setDescription(product.getDescription());
        response.setCategory(product.getCategory());
        response.setBrand(product.getBrand());
        response.setGender(product.getGender());
        response.setFabric(product.getFabric());
        response.setStyle(product.getStyle());
        response.setSeason(product.getSeason());
        response.setActive(product.isActive());
        response.setCreatedAt(product.getCreatedAt());
        response.setUpdatedAt(product.getUpdatedAt());

        return response;
    }
}