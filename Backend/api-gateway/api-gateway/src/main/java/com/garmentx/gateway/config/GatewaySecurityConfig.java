package com.garmentx.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Configuration
public class GatewaySecurityConfig {

    @Value("${jwt.secret}")
    private String secret;

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {

        SecretKeySpec key = new SecretKeySpec(
                secret.getBytes(StandardCharsets.UTF_8),
                "HmacSHA256"
        );

        return NimbusReactiveJwtDecoder
                .withSecretKey(key)
                .macAlgorithm(
                        org.springframework.security.oauth2.jose.jws.MacAlgorithm.HS256
                )
                .build();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http) {

        return http
                .csrf(csrf -> csrf.disable())

                .authorizeExchange(exchange -> exchange

                        .pathMatchers(
                                "/auth/login",
                                "/auth/register"
                        ).permitAll()

                        .pathMatchers(
                                HttpMethod.GET,
                                "/catalog/products",
                                "/catalog/products/**"
                        ).permitAll()

                        .pathMatchers("/auth/admin")
                        .hasRole("ADMIN")

                        .anyExchange()
                        .authenticated()
                )

                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt ->
                                jwt.jwtAuthenticationConverter(
                                        jwtAuthenticationConverter()
                                )
                        )
                )

                .build();
    }

    @Bean
    public ReactiveJwtAuthenticationConverterAdapter jwtAuthenticationConverter() {

        JwtAuthenticationConverter converter =
                new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(jwt -> {

            String role = jwt.getClaimAsString("role");

            if (role == null) {
                return java.util.List.of();
            }

            return java.util.List.of(
                    new org.springframework.security.core.authority.SimpleGrantedAuthority(
                            "ROLE_" + role
                    )
            );
        });

        return new ReactiveJwtAuthenticationConverterAdapter(converter);
    }
}