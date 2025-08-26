package ecomhub.authservice.infrastructure.inbound.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import ecomhub.authservice.common.dto.response.ApiResponse;
import ecomhub.authservice.common.enums.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        log.error("Access denied: {}", accessDeniedException.getMessage());
        ApiResponse<?> apiResponse = ApiResponse.error(
                response.getStatus(),
                ErrorCode.FORBIDDEN.name(),
                "You do not have sufficient permissions to perform this action."
        );

        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }
}
