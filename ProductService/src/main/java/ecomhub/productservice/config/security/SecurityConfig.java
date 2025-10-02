package ecomhub.productservice.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import ecomhub.productservice.config.security.converter.CustomReactiveJwtAuthenticationConverter;
import ecomhub.productservice.config.security.exception.CustomAccessDeniedHandler;
import ecomhub.productservice.config.security.exception.CustomAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.reactive.CorsConfigurationSource;

@Configuration // đánh dấu class cấu hình Spring
@EnableWebFluxSecurity // bật bảo mật cho WebFlux
@EnableMethodSecurity // cho phép @PreAuthorize, @RolesAllowed ...
public class SecurityConfig {

    // Những endpoint public, không cần auth
    private static final String[] PUBLIC_URLS = {"/actuator/**"};

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
                                                         CorsConfigurationSource corsSource,
                                                         CustomReactiveJwtAuthenticationConverter converter,
                                                         ObjectMapper objectMapper) {
        return http
                // Cấu hình CORS cho phép frontend gọi API
                .cors(cors -> cors.configurationSource(corsSource))
                // Tắt CSRF (dùng cho REST API)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                // Cấu hình quyền truy cập
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(PUBLIC_URLS).permitAll() // các endpoint public
                        .anyExchange().authenticated() // còn lại phải có JWT
                )
                // Bật Resource Server để validate JWT
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(converter)) // custom converter
                )
                // Xử lý lỗi 401 & 403 bằng custom handler
                .exceptionHandling(handling -> handling
                        .accessDeniedHandler(new CustomAccessDeniedHandler(objectMapper)) // lỗi 403
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint(objectMapper)) // lỗi 401
                )
                .build();
    }
}
