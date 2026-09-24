package com.garmentx.catalog.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class ProductVariantRequest {

    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotBlank(message = "SKU is required")
    @Size(max = 100, message = "SKU must not exceed 100 characters")
    private String sku;

    @Size(max = 100, message = "Barcode must not exceed 100 characters")
    private String barcode;

    @NotBlank(message = "Size is required")
    private String size;

    @NotBlank(message = "Color is required")
    private String color;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false,
            message = "Price must be greater than 0")
    private BigDecimal price;

    @DecimalMin(value = "0.0", inclusive = true,
            message = "Discount cannot be negative")
    private BigDecimal discount;

    @DecimalMin(value = "0.0", inclusive = true,
            message = "Tax cannot be negative")
    private BigDecimal tax;

    private boolean active = true;

    public ProductVariantRequest() {
    }

    // Generate getters and setters

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public @DecimalMin(value = "0.0", inclusive = true,
            message = "Tax cannot be negative") BigDecimal getTax() {
        return tax;
    }

    public void setTax(@DecimalMin(value = "0.0", inclusive = true,
            message = "Tax cannot be negative") BigDecimal tax) {
        this.tax = tax;
    }

    public @DecimalMin(value = "0.0", inclusive = true,
            message = "Discount cannot be negative") BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(@DecimalMin(value = "0.0", inclusive = true,
            message = "Discount cannot be negative") BigDecimal discount) {
        this.discount = discount;
    }

    public @NotNull(message = "Price is required") @DecimalMin(value = "0.0", inclusive = false,
            message = "Price must be greater than 0") BigDecimal getPrice() {
        return price;
    }

    public void setPrice(@NotNull(message = "Price is required") @DecimalMin(value = "0.0", inclusive = false,
            message = "Price must be greater than 0") BigDecimal price) {
        this.price = price;
    }

    public @NotBlank(message = "Color is required") String getColor() {
        return color;
    }

    public void setColor(@NotBlank(message = "Color is required") String color) {
        this.color = color;
    }

    public @NotBlank(message = "Size is required") String getSize() {
        return size;
    }

    public void setSize(@NotBlank(message = "Size is required") String size) {
        this.size = size;
    }

    public @Size(max = 100, message = "Barcode must not exceed 100 characters") String getBarcode() {
        return barcode;
    }

    public void setBarcode(@Size(max = 100, message = "Barcode must not exceed 100 characters") String barcode) {
        this.barcode = barcode;
    }

    public @NotBlank(message = "SKU is required") @Size(max = 100, message = "SKU must not exceed 100 characters") String getSku() {
        return sku;
    }

    public void setSku(@NotBlank(message = "SKU is required") @Size(max = 100, message = "SKU must not exceed 100 characters") String sku) {
        this.sku = sku;
    }

    public @NotNull(message = "Product ID is required") Long getProductId() {
        return productId;
    }

    public void setProductId(@NotNull(message = "Product ID is required") Long productId) {
        this.productId = productId;
    }
}