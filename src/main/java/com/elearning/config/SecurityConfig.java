package com.elearning.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.elearning.security.CustomUserDetailsService;
import com.elearning.security.JwtAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(
            CustomUserDetailsService userDetailsService,
            JwtAuthenticationFilter jwtAuthenticationFilter) {

        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration =
                new CorsConfiguration();

        configuration.setAllowedOrigins(
                List.of(
                        "http://localhost:5173",
                        "https://elearning-frontend-murex-five.vercel.app"
                )
        );

        configuration.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "OPTIONS"
                )
        );

        configuration.setAllowedHeaders(
                List.of("*")
        );

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(
                passwordEncoder()
        );

        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .cors(cors -> {})

            .httpBasic(httpBasic ->
                httpBasic.disable()
            )

            .formLogin(formLogin ->
                formLogin.disable()
            )

            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            )

            .authorizeHttpRequests(auth -> auth

                .requestMatchers(
                    "/api/auth/**"
                ).permitAll()

                .requestMatchers(
                    HttpMethod.POST,
                    "/api/courses"
                ).hasRole("TRAINER")

                .requestMatchers(
                    HttpMethod.PUT,
                    "/api/courses/*"
                ).hasRole("TRAINER")

                .requestMatchers(
                    HttpMethod.GET,
                    "/api/courses/my-courses"
                ).hasRole("TRAINER")

                .requestMatchers(
                    HttpMethod.POST,
                    "/api/courses/*/lessons"
                ).hasRole("TRAINER")

                .requestMatchers(
                    HttpMethod.PUT,
                    "/api/courses/*/lessons/*"
                ).hasRole("TRAINER")

                .requestMatchers(
                    HttpMethod.DELETE,
                    "/api/courses/*/lessons/*"
                ).hasRole("TRAINER")

                .requestMatchers(
                    HttpMethod.POST,
                    "/api/enrollments/*"
                ).hasRole("STUDENT")

                .requestMatchers(
                    HttpMethod.GET,
                    "/api/enrollments/my-enrollments"
                ).hasRole("STUDENT")

                .requestMatchers(
                    HttpMethod.POST,
                    "/api/progress/lessons/*/complete"
                ).hasRole("STUDENT")

                .requestMatchers(
                    HttpMethod.GET,
                    "/api/progress/courses/*"
                ).hasRole("STUDENT")

                .requestMatchers(
                    HttpMethod.POST,
                    "/api/payments/create-order/*"
                ).hasRole("STUDENT")

                .requestMatchers(
                    HttpMethod.POST,
                    "/api/payments/verify/*"
                ).hasRole("STUDENT")

                .requestMatchers(
                    HttpMethod.POST,
                    "/api/comments/course/*"
                ).authenticated()

                .requestMatchers(
                    HttpMethod.POST,
                    "/api/comments/*/reply"
                ).authenticated()

                .requestMatchers(
                    HttpMethod.GET,
                    "/api/comments/course/*"
                ).authenticated()

                .requestMatchers(
                    "/api/student-test"
                ).hasRole("STUDENT")

                .requestMatchers(
                    "/api/trainer-test"
                ).hasRole("TRAINER")

                .requestMatchers(
                    "/api/admin/**"
                ).hasRole("ADMIN")

                .requestMatchers(
                    "/api/admin-test"
                ).hasRole("ADMIN")

                .anyRequest().authenticated()
            )

            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}