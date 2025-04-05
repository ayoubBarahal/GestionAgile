package project.gestionprojet.Configuration;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtTokenProvider tokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);

            logger.info("Request URL: " + request.getRequestURL());
            logger.info("Authorization Header: " + request.getHeader("Authorization"));

            if (StringUtils.hasText(jwt)) {
                logger.info("Token found: " + jwt);

                if (tokenProvider.validateToken(jwt)) {
                    Authentication authentication = tokenProvider.getAuthentication(jwt);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    logger.info("Token validated successfully");
                } else {
                    logger.warn("Token validation failed");
                    response.setHeader("X-Token-Error", "Invalid Token");
                }
            } else {
                logger.warn("No JWT token found in the request");
            }
        } catch (Exception ex) {
            logger.error("Authentication process failed", ex);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed: " + ex.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
