package com.garmentx.catalog.service;

import com.garmentx.catalog.dto.CategoryRequest;
import com.garmentx.catalog.dto.CategoryResponse;
import com.garmentx.catalog.entity.Category;
import com.garmentx.catalog.exception.ResourceNotFoundException;
import com.garmentx.catalog.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {

        String code = request.getCode().trim();

        if (categoryRepository.existsByCode(code)) {
            throw new IllegalArgumentException(
                    "Category already exists with code: " + code
            );
        }

        Category parentCategory = null;

        if (request.getParentCategoryId() != null) {

            parentCategory = categoryRepository.findById(
                    request.getParentCategoryId()
            ).orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Parent category not found with id: "
                                    + request.getParentCategoryId()
                    )
            );

            if (!parentCategory.isActive()) {
                throw new IllegalArgumentException(
                        "Cannot create category under an inactive parent category"
                );
            }
        }

        Category category = new Category();

        category.setName(request.getName().trim());
        category.setCode(code);
        category.setParentCategory(parentCategory);
        category.setDescription(request.getDescription());
        category.setImageUrl(request.getImageUrl());

        category.setDisplayOrder(
                request.getDisplayOrder() != null
                        ? request.getDisplayOrder()
                        : 0
        );

        category.setActive(request.isActive());

        Category savedCategory =
                categoryRepository.save(category);

        return toResponse(savedCategory);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {

        return categoryRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id: " + id
                        )
                );

        return toResponse(category);
    }

    @Override
    public CategoryResponse updateCategory(
            Long id,
            CategoryRequest request
    ) {

        Category existingCategory =
                categoryRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Category not found with id: " + id
                                )
                        );

        String newCode = request.getCode().trim();

        if (!existingCategory.getCode().equals(newCode)
                && categoryRepository.existsByCode(newCode)) {

            throw new IllegalArgumentException(
                    "Category already exists with code: " + newCode
            );
        }

        Category parentCategory = null;

        if (request.getParentCategoryId() != null) {

            if (id.equals(request.getParentCategoryId())) {
                throw new IllegalArgumentException(
                        "Category cannot be its own parent"
                );
            }

            parentCategory = categoryRepository.findById(
                    request.getParentCategoryId()
            ).orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Parent category not found with id: "
                                    + request.getParentCategoryId()
                    )
            );

            if (!parentCategory.isActive()) {
                throw new IllegalArgumentException(
                        "Cannot assign an inactive parent category"
                );
            }
        }

        existingCategory.setName(request.getName().trim());
        existingCategory.setCode(newCode);
        existingCategory.setParentCategory(parentCategory);
        existingCategory.setDescription(request.getDescription());
        existingCategory.setImageUrl(request.getImageUrl());

        existingCategory.setDisplayOrder(
                request.getDisplayOrder() != null
                        ? request.getDisplayOrder()
                        : 0
        );

        existingCategory.setActive(request.isActive());

        Category updatedCategory =
                categoryRepository.save(existingCategory);

        return toResponse(updatedCategory);
    }

    @Override
    public void deleteCategory(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id: " + id
                        )
                );

        categoryRepository.delete(category);
    }

    private CategoryResponse toResponse(Category category) {

        CategoryResponse response = new CategoryResponse();

        response.setId(category.getId());
        response.setName(category.getName());
        response.setCode(category.getCode());
        response.setDescription(category.getDescription());
        response.setImageUrl(category.getImageUrl());
        response.setDisplayOrder(category.getDisplayOrder());
        response.setActive(category.isActive());

        if (category.getParentCategory() != null) {

            response.setParentCategoryId(
                    category.getParentCategory().getId()
            );

            response.setParentCategoryName(
                    category.getParentCategory().getName()
            );
        }

        return response;
    }
}