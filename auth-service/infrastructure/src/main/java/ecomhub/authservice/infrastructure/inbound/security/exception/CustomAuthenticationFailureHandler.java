package ecomhub.authservice.infrastructure.inbound.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import ecomhub.authservice.common.dto.response.ApiResponse;
import ecomhub.authservice.common.enums.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        if (request.getRequestURI().startsWith("/login") && request.getMethod().equals("POST"))
            response.sendRedirect("/login?error" + "=" + exception.getMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ApiResponse<?> apiResponse;

        if (exception instanceof OAuth2AuthenticationException oAuth2Ex) {
            apiResponse = ApiResponse.error(
                    response.getStatus(),
                    oAuth2Ex.getError().getErrorCode(),
                    oAuth2Ex.getError().getDescription()
            );
        } else {
            apiResponse = ApiResponse.error(
                    response.getStatus(),
                    ErrorCode.UNAUTHORIZED.name(),
                    exception.getMessage()
            );
        }
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        response.getWriter().flush();
    }
}
