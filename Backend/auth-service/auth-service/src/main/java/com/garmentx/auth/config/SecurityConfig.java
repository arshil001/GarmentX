package com.garmentx.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.garmentx.auth.security.JwtAuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


        @Bean
        public SecurityFilterChain securityFilterChain(
                HttpSecurity http) throws Exception {

            http
                    .csrf(csrf -> csrf.disable())

                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers(
                                    "/swagger-ui/**",
                                    "/swagger-ui.html",
                                    "/v3/api-docs/**"
                            ).permitAll()
                            .requestMatchers(
                                    "/auth/register",
                                    "/auth/login"
                            ).permitAll()
                            .requestMatchers("/auth/admin")
                            .hasRole("ADMIN")
                            .anyRequest().authenticated()
                    )

                    .exceptionHandling(exception -> exception

                            .authenticationEntryPoint(
                                    (request, response, authException) -> {
                                        response.sendError(
                                                401,
                                                "Unauthorized"
                                        );
                                    }
                            )

                            .accessDeniedHandler(
                                    (request, response, accessDeniedException) -> {
                                        response.sendError(
                                                403,
                                                "Forbidden"
                                        );
                                    }
                            )
                    )

                    .addFilterBefore(
                            jwtAuthenticationFilter,
                            UsernamePasswordAuthenticationFilter.class
                    );

            return http.build();
        }

}