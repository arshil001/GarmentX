package com.garmentx.catalog.dto;

import com.garmentx.catalog.entity.BrandStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class BrandRequest {

    @NotBlank(message = "Brand name is required")
    @Size(
            min = 2,
            max = 150,
            message = "Brand name must be between 2 and 150 characters"
    )
    private String name;

    @NotBlank(message = "Brand code is required")
    @Size(
            min = 2,
            max = 50,
            message = "Brand code must be between 2 and 50 characters"
    )
    @Pattern(
            regexp = "^[A-Za-z0-9_-]+$",
            message = "Brand code can contain only letters, numbers, hyphen and underscore"
    )
    private String code;

    @Size(
            max = 1000,
            message = "Description cannot exceed 1000 characters"
    )
    private String description;

    @Size(
            max = 500,
            message = "Logo URL cannot exceed 500 characters"
    )
    private String logo;

    @Size(
            max = 500,
            message = "Website URL cannot exceed 500 characters"
    )
    private String website;

    private BrandStatus status;

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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public BrandStatus getStatus() {
        return status;
    }

    public void setStatus(BrandStatus status) {
        this.status = status;
    }
}