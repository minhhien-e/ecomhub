package ecomhub.authservice.infrastructure.inbound.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import ecomhub.authservice.common.dto.response.ApiResponse;
import ecomhub.authservice.common.enums.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (request.getRequestURI().startsWith("/oauth2/authorize") && request.getMethod().equals("GET")) {
            response.sendRedirect("/login");
            return;
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ApiResponse<?> apiResponse;
        apiResponse = ApiResponse.error(
                response.getStatus(),
                ErrorCode.UNAUTHORIZED.name(),
                authException.getMessage()
        );
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        response.getWriter().flush();
    }
}
