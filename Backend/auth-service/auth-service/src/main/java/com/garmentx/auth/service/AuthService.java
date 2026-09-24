package com.garmentx.auth.service;

import com.garmentx.auth.dto.RegisterRequest;
import com.garmentx.auth.entity.Role;
import com.garmentx.auth.entity.User;
import com.garmentx.auth.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.garmentx.auth.dto.LoginRequest;
import org.springframework.security.authentication.BadCredentialsException;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            return "Email already registered";
        }

        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        // Password hashing
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        // Default role
        user.setRole(Role.CUSTOMER);

        user.setEnabled(true);

        userRepository.save(user);

        return "User registered successfully";
    }


    public String login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new BadCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new BadCredentialsException(
                    "Invalid email or password"
            );
        }

        if (!user.isEnabled()) {
            throw new BadCredentialsException("User account is disabled");
        }

        return jwtService.generateToken(user);
    }
    public User getUserByEmail(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new BadCredentialsException("User not found"));
    }
}