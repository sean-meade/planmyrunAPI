package ie.planmyrun.api.planmyrunAPI.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import ie.planmyrun.api.planmyrunAPI.dto.ErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Protects /api/account/** with an API key (X-API-Key header).
 * Used for server-to-server calls (e.g. Retell voice AI backend).
 * Returns 401 if the key is missing or invalid.
 */
@Component
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    private static final String API_KEY_HEADER = "X-API-Key";
    private static final String ACCOUNT_PATH_PREFIX = "/api/account/";

    private static final Logger log = LoggerFactory.getLogger(ApiKeyAuthFilter.class);

    @Value("${retell.api.key:}")
    private String configuredApiKey;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (!request.getRequestURI().startsWith(ACCOUNT_PATH_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        String apiKey = request.getHeader(API_KEY_HEADER);
        if (configuredApiKey == null || configuredApiKey.isBlank()) {
            log.warn("retell.api.key is not set; rejecting account API request");
            sendUnauthorized(response, "API key not configured. Contact support.");
            return;
        }
        if (apiKey == null || apiKey.isBlank() || !configuredApiKey.equals(apiKey.trim())) {
            log.info("Account API request rejected: missing or invalid API key");
            sendUnauthorized(response, "Invalid or missing API key. Contact support.");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void sendUnauthorized(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(
            new ErrorResponse(message, "UNAUTHORIZED")
        ));
    }
}
