package ec.edu.ups.icc.proyectofinal.project.security.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import ec.edu.ups.icc.proyectofinal.project.security.config.JwtProperties;
import ec.edu.ups.icc.proyectofinal.project.security.services.UserDetailsServiceImpl;
import ec.edu.ups.icc.proyectofinal.project.security.utils.JwtUtil;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtProperties jwtProperties;

    public JwtAuthenticationFilter(JwtUtil jwtUtil,
                                 UserDetailsServiceImpl userDetailsService,
                                 JwtProperties jwtProperties) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.jwtProperties = jwtProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);

            if (StringUtils.hasText(jwt) && jwtUtil.validateToken(jwt)) {
                String email = jwtUtil.getEmailFromToken(jwt);
                
                // Log para debuguear en la consola de Java
                logger.info("JWT detectado. Email extraído: {}", email);

                if (email != null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    logger.info("Usuario autenticado exitosamente: {}", email);
                }
            }
        } catch (Exception ex) {
            logger.error("No se pudo establecer la autenticación del usuario", ex);
        }

        filterChain.doFilter(request, response);
    }

    // Cambia temporalmente esto en JwtAuthenticationFilter.java para probar:
private String getJwtFromRequest(HttpServletRequest request) {
    // En lugar de usar jwtProperties, escribimos los strings directamente
    String bearerToken = request.getHeader("Authorization"); 

    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
        return bearerToken.substring(7).trim();
    }
    return null;
}
}