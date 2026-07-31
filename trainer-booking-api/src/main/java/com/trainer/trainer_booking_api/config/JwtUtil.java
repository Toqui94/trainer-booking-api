package com.trainer.trainer_booking_api.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component  // Spring la registra como un "componente" usable en cualquier parte
public class JwtUtil {

    // Lee el valor de application.properties
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    // Crea la llave secreta a partir del texto
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // ========== GENERAR TOKEN (La taquilla te da el boleto) ==========
    public String generateToken(String correo) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .subject(correo)                    // El "dueño" del boleto (su correo)
                .issuedAt(now)                      // Fecha de emisión
                .expiration(expiryDate)             // Fecha de vencimiento
                .signWith(getSigningKey())          // Firma con la llave secreta
                .compact();                         // Convierte a String
    }

    // ========== EXTRAER EL CORREO DEL TOKEN (Leer quién es el dueño) ==========
    public String extractCorreo(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    // ========== VALIDAR TOKEN (El escáner del concierto) ==========
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;  // Si no explota, el token es válido
        } catch (Exception e) {
            return false; // Token inválido, modificado o vencido
        }
    }

    // ========== VERIFICAR SI ESTÁ VENCIDO ==========
    public boolean isTokenExpired(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getExpiration().before(new Date());
    }
}