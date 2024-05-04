package com.sibghat.vape_shop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.security.SecureRandom;
import java.util.List;

@Configuration
@EnableMethodSecurity()
public class SecurityConfig {

    @Value("${jwksUri}")
    private String jwksUri;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.oauth2ResourceServer(
                (oauth2) -> oauth2.jwt(
                        jwt -> jwt.jwkSetUri(jwksUri)
                                .jwtAuthenticationConverter(new CustomeJwtAuthenticationTokenConverter())
                )
        );

        http.csrf(AbstractHttpConfigurer::disable);

        http.cors(c -> {
            CorsConfigurationSource source = s -> {
                CorsConfiguration cc = new CorsConfiguration();
                cc.setAllowCredentials(true);
                cc.setAllowedOrigins(List.of("http://127.0.0.1:4200"));
                cc.setAllowedHeaders(List.of("*"));
                cc.setAllowedMethods(List.of("*"));
                return cc;
            };
            c.configurationSource(source);
        });

        http.authorizeHttpRequests(req -> req
                .requestMatchers(HttpMethod.POST,"/users").permitAll()
                .requestMatchers(HttpMethod.PATCH,"/users/verify/*").permitAll()
                .requestMatchers(HttpMethod.PATCH, "/users/enable/*").permitAll()
                .anyRequest().authenticated());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        int strength = 10;
        return new BCryptPasswordEncoder(strength, new SecureRandom());
    }

}
