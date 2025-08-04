package ecomhub.authservice.infrastructure.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import ecomhub.authservice.common.dto.ApiResponse;
import ecomhub.authservice.common.enums.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
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
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ApiResponse<?> apiResponse = ApiResponse.error(
                response.getStatus(),
                ErrorCode.INVALID_CREDENTIALS.name(),
                exception.getMessage()
        );

        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }
}
