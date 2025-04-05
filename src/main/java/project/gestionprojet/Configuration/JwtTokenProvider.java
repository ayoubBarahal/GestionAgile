package project.gestionprojet.Configuration;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import project.gestionprojet.DTO.UserDetailsImpl;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;


@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    public String generateToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        String tokken = Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .claim("role", userPrincipal.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .findFirst()
                        .orElse("ROLE_USER"))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), SignatureAlgorithm.HS512)
                .compact();
        System.out.println("Token : " + tokken );
        return tokken;
    }


    public Authentication getAuthentication(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();
            String roleString = claims.get("role", String.class);

            String role = extractRoleFromString(roleString);

            Collection<GrantedAuthority> authorities = Collections.singletonList(
                    new SimpleGrantedAuthority(role)
            );

            UserDetails principal = User.withUsername(username)
                    .password("")
                    .authorities(authorities)
                    .build();

            return new UsernamePasswordAuthenticationToken(principal, token, authorities);
        } catch (Exception ex) {
            logger.error("JWT Authentication error", ex);
            throw ex;
        }
    }

    private String extractRoleFromString(String roleString) {
        if (roleString != null) {
            int startIndex = roleString.indexOf("name=") + 5;
            int endIndex = roleString.indexOf(")", startIndex);
            if (startIndex != -1 && endIndex != -1) {
                return roleString.substring(startIndex, endIndex);
            }
        }
        return "ROLE_USER";
    }


    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature: {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty");
        }
        return false;
    }

}