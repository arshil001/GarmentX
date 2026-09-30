package com.garmentx.catalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoryRequest {

    @NotBlank(message = "Category name is required")
    @Size(
            max = 100,
            message = "Category name must not exceed 100 characters"
    )
    private String name;

    @NotBlank(message = "Category code is required")
    @Size(
            max = 50,
            message = "Category code must not exceed 50 characters"
    )
    private String code;

    private Long parentCategoryId;

    @Size(
            max = 500,
            message = "Description must not exceed 500 characters"
    )
    private String description;

    @Size(
            max = 500,
            message = "Image URL must not exceed 500 characters"
    )
    private String imageUrl;

    private Integer displayOrder = 0;

    private boolean active = true;

    public @NotBlank(message = "Category name is required") @Size(
            max = 100,
            message = "Category name must not exceed 100 characters"
    ) String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Category name is required") @Size(
            max = 100,
            message = "Category name must not exceed 100 characters"
    ) String name) {
        this.name = name;
    }

    public @NotBlank(message = "Category code is required") @Size(
            max = 50,
            message = "Category code must not exceed 50 characters"
    ) String getCode() {
        return code;
    }

    public void setCode(@NotBlank(message = "Category code is required") @Size(
            max = 50,
            message = "Category code must not exceed 50 characters"
    ) String code) {
        this.code = code;
    }

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public @Size(
            max = 500,
            message = "Description must not exceed 500 characters"
    ) String getDescription() {
        return description;
    }

    public void setDescription(@Size(
            max = 500,
            message = "Description must not exceed 500 characters"
    ) String description) {
        this.description = description;
    }

    public @Size(
            max = 500,
            message = "Image URL must not exceed 500 characters"
    ) String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(@Size(
            max = 500,
            message = "Image URL must not exceed 500 characters"
    ) String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}