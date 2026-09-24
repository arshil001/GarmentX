package com.garmentx.catalog.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    @Value("${catalog.message}")
    private String message;

    @Value("${catalog.name}")
    private String serviceName;

    @GetMapping("/test")
    public String test() {
        return serviceName + " | " + message;
    }

    @PostMapping("/admin/products")
    public ResponseEntity<String> adminProduct() {

        return ResponseEntity.ok(
                "Admin and Super_Admin can access product management"
        );
    }

}