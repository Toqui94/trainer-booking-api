package com.trainer.trainer_booking_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
                          CustomUserDetailsService customUserDetailsService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customUserDetailsService = customUserDetailsService;
    }

    // ========== 1. ENCRIPTADOR DE PASSWORDS ==========
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

// ========== 2. PROVEEDOR DE AUTENTICACIÓN ==========
@Bean
public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider(customUserDetailsService);
    provider.setPasswordEncoder(passwordEncoder());
    return provider;
}

    // ========== 3. AUTHENTICATION MANAGER (Para el login) ==========
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // ========== 4. CADENA DE FILTROS DE SEGURIDAD ==========
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Desactivar CSRF (no lo necesitamos en APIs REST stateless)
            .csrf(AbstractHttpConfigurer::disable)
            
            // Configurar autorización de URLs
            .authorizeHttpRequests(auth -> auth
                // URLs PÚBLICAS (no necesitan token)
                .requestMatchers("/api/auth/**").permitAll()      // Login y registro
                .requestMatchers("/api/health").permitAll()       // Health check
                
                // URLs PROTEGIDAS (necesitan token)
                .requestMatchers("/api/usuarios/**").authenticated()
                .requestMatchers("/api/entrenadores/**").authenticated()
                .requestMatchers("/api/roles/**").authenticated()
                .requestMatchers("/api/especialidades/**").authenticated()
                
                // Cualquier otra URL requiere autenticación
                .anyRequest().authenticated()
            )
            
            // No crear sesiones en el servidor (somos stateless, usamos JWT)
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            
            // Agregar nuestro filtro JWT ANTES del filtro de username/password
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}