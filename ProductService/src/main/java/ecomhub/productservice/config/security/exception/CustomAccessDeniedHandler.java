package ecomhub.productservice.config.security.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Xử lý khi user đã đăng nhập nhưng không đủ quyền (role).
 * Trả về HTTP 403 Forbidden.
 */
public class CustomAccessDeniedHandler implements ServerAccessDeniedHandler {

    private final ObjectMapper objectMapper;

    public CustomAccessDeniedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException ex) {
        // Set status 403
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        // JSON thông báo lỗi
        String json = "{\"error\": \"Access Denied\", \"message\": \"You do not have permission to access this resource\"}";

        // Ghi JSON ra response
        return Mono.fromCallable(() -> objectMapper.writeValueAsBytes(json))
                .onErrorResume(JsonProcessingException.class, e -> Mono.just(new byte[0]))
                .flatMap(bytes -> exchange.getResponse()
                        .writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(bytes))));
    }
}
