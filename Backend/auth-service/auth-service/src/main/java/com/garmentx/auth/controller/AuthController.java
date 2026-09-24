package com.garmentx.auth.controller;

import com.garmentx.auth.dto.RegisterRequest;
import com.garmentx.auth.entity.User;
import com.garmentx.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.garmentx.auth.dto.LoginRequest;
import com.garmentx.auth.dto.LoginResponse;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody RegisterRequest request) {

        String response = authService.register(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {

        String token = authService.login(request);

        User user = authService.getUserByEmail(request.getEmail());

        LoginResponse response = new LoginResponse(
                token,
                "Bearer",
                user.getRole().name()
        );

        return ResponseEntity.ok(response);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/profile")
    public ResponseEntity<String> profile() {
        return ResponseEntity.ok("You are authenticated!");
    }
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/admin")
    public ResponseEntity<String> adminOnly() {
        return ResponseEntity.ok("Welcome Admin!");
    }
}