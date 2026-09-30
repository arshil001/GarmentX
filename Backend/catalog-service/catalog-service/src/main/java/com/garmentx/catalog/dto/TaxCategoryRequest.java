package com.garmentx.catalog.dto;

import com.garmentx.catalog.entity.TaxStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class TaxCategoryRequest {

    @NotBlank(message = "Tax category name is required")
    @Size(max = 100, message = "Tax category name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "Tax category code is required")
    @Size(max = 30, message = "Tax category code must not exceed 30 characters")
    @Pattern(
            regexp = "^[A-Za-z0-9_-]+$",
            message = "Tax category code can contain only letters, numbers, underscore and hyphen"
    )
    private String code;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    private TaxStatus status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaxStatus getStatus() {
        return status;
    }

    public void setStatus(TaxStatus status) {
        this.status = status;
    }
}