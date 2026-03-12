package com.datacenter.mallaturnos.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuración de seguridad de la aplicación
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    /**
     * Configura las reglas de seguridad HTTP
     * Por ahora, permite acceso a todos los endpoints sin autenticación
     */
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                    .anyRequest().permitAll()  // Permite todos los endpoints (sin autenticación)
            )
            .csrf(csrf -> csrf.disable());  // Desactiva CSRF (útil para desarrollo)
        
        return http.build();
    }
}
