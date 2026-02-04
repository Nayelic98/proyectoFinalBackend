package ec.edu.ups.icc.proyectofinal.project.security.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import ec.edu.ups.icc.proyectofinal.project.security.config.JwtProperties;
import ec.edu.ups.icc.proyectofinal.project.security.services.UserDetailsImpl;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    private final JwtProperties jwtProperties;
    private final SecretKey key;

    public JwtUtil(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    public String generateToken(Authentication authentication) {
        // 1. Extraer información del usuario autenticado
        // Cast seguro porque siempre retorna UserDetailsImpl
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        // 2. Calcular fechas de emisión y expiración
        Date now = new Date(); // Fecha actual
        Date expiryDate = new Date(now.getTime() + jwtProperties.getExpirationMs());

        // 3. Extraer roles del usuario y convertir a String
        String roles = userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority) 
                .collect(Collectors.joining(","));

        // 4. Construir y firmar el token JWT
        return Jwts.builder()
                // Subject: Identificador único del usuario (su ID)
                .subject(String.valueOf(userPrincipal.getId())) 

                // Claims personalizados ajustados a tus parámetros
                .claim("contacto", userPrincipal.getUsername()) 
                .claim("nombre", userPrincipal.getNombre()) 
                .claim("roles", roles) 

                // Issuer: Quién emitió el token
                .issuer(jwtProperties.getIssuer()) 

                // Fechas
                .issuedAt(now) 
                .expiration(expiryDate) 

                // Firma digital con algoritmo HS256
                .signWith(key, Jwts.SIG.HS256) 

                // Compactar
                .compact(); 
    }

    public String generateTokenFromUserDetails(UserDetailsImpl userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtProperties.getExpirationMs());

        String roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .subject(String.valueOf(userDetails.getId()))
                .claim("contacto", userDetails.getUsername())
                .claim("nombre", userDetails.getNombre())
                .claim("roles", roles)
                .issuer(jwtProperties.getIssuer())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    public Long getUserIdFromToken(String token) {
        // 1. Parsear y validar el token
        Claims claims = Jwts.parser()
                .verifyWith(key) 
                .build() 
                .parseSignedClaims(token) 
                .getPayload(); 

        // 2. Extraer el subject (ID del usuario)
        return Long.parseLong(claims.getSubject());
    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        // Extraer claim "contacto" (que es el parámetro que tienes)
        return claims.get("contacto", String.class);
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser()
                    .verifyWith(key) 
                    .build()
                    .parseSignedClaims(authToken);

            return true;

        } catch (SignatureException ex) {
            logger.error("Firma JWT inválida: {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            logger.error("Token JWT malformado: {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            logger.error("Token JWT expirado: {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            logger.error("Token JWT no soportado: {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string está vacío: {}", ex.getMessage());
        }

        return false;
    }
}