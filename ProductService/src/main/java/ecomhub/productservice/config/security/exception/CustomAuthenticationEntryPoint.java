package ecomhub.productservice.config.security.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Xử lý khi user chưa đăng nhập hoặc token invalid.
 * Trả về HTTP 401 Unauthorized.
 */
public class CustomAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public CustomAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        // Set status 401
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        // JSON thông báo lỗi
        String json = "{\"error\": \"Unauthorized\", \"message\": \"Full authentication is required to access this resource\"}";

        // Ghi JSON ra response
        return Mono.fromCallable(() -> objectMapper.writeValueAsBytes(json))
                .onErrorResume(JsonProcessingException.class, e -> Mono.just(new byte[0]))
                .flatMap(bytes -> exchange.getResponse()
                        .writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(bytes))));
    }
}
