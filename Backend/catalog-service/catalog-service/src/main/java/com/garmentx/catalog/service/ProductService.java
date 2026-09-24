package com.garmentx.catalog.service;

import com.garmentx.catalog.dto.ProductRequest;
import com.garmentx.catalog.dto.ProductResponse;
import com.garmentx.catalog.entity.Product;
import com.garmentx.catalog.exception.ResourceNotFoundException;
import com.garmentx.catalog.mapper.ProductMapper;
import com.garmentx.catalog.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(
            ProductRepository productRepository,
            ProductMapper productMapper) {

        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public ProductResponse createProduct(ProductRequest request) {

        if (productRepository.existsByProductCode(
                request.getProductCode())) {

            throw new IllegalArgumentException(
                    "Product already exists with code: "
                            + request.getProductCode()
            );
        }

        Product product = productMapper.toEntity(request);

        Product savedProduct =
                productRepository.save(product);

        return productMapper.toResponse(savedProduct);
    }

    public List<ProductResponse> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    public ProductResponse getProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id: " + id
                        )
                );

        return productMapper.toResponse(product);
    }

    public ProductResponse updateProduct(
            Long id,
            ProductRequest request) {

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id: " + id
                        )
                );

        existingProduct.setName(request.getName());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setCategory(request.getCategory());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setGender(request.getGender());
        existingProduct.setFabric(request.getFabric());
        existingProduct.setStyle(request.getStyle());
        existingProduct.setSeason(request.getSeason());
        existingProduct.setActive(request.isActive());

        Product updatedProduct =
                productRepository.save(existingProduct);

        return productMapper.toResponse(updatedProduct);
    }

    public void deleteProduct(Long id) {

        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Product not found with id: " + id
            );
        }

        productRepository.deleteById(id);
    }
}