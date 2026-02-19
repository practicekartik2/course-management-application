package com.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.apigateway.security.JwtAuthWebFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthWebFilter jwtAuthWebFilter;

    public SecurityConfig(JwtAuthWebFilter jwtAuthWebFilter) {
        this.jwtAuthWebFilter = jwtAuthWebFilter;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

       return http
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange(exchange -> exchange
                    .pathMatchers("/auth/**").permitAll()
                    .anyExchange().authenticated()
            )
            .addFilterAt(jwtAuthWebFilter, SecurityWebFiltersOrder.FIRST)
            .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
            .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
            .build();
    }
}

