package ecomhub.productservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)  // tắt CSRF
                .authorizeExchange(exchange -> exchange
                        .anyExchange().permitAll()           // cho phép tất cả request
                )
                .build();
    }
}

//package ecomhub.productservice.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
//import org.springframework.security.oauth2.jwt.ReactiveJwtDecoders;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
//import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.oauth2.jwt.Jwt;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Configuration
//@EnableWebFluxSecurity   // Bật module bảo mật reactive cho WebFlux
//@EnableMethodSecurity    // Cho phép dùng @PreAuthorize, @PostAuthorize, @Secured trong code
//public class SecurityConfig {
//
//    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
//    private String issuerUri;
//
//    /**
//     * Định nghĩa một SecurityWebFilterChain:
//     * - Là chuỗi các filter mà mọi request vào service đều phải đi qua.
//     * - Mỗi filter có thể quyết định cho đi tiếp hay chặn lại.
//     */
//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//        return http
//                // Tắt CSRF (Cross-Site Request Forgery)
//                .csrf(ServerHttpSecurity.CsrfSpec::disable)
//                // Quy định quyền truy cập cho các endpoint
//                .authorizeExchange(exchange -> exchange
//                        // Cho phép tất cả request vào /api/v1/public/** mà không cần login
//                        .pathMatchers("/api/v1/public/**").permitAll()
//                        // Các request khác bắt buộc phải xác thực (có token, login,...)
//                        .anyExchange().authenticated()
//                )
//                .oauth2ResourceServer(oauth2 -> oauth2
//                        .jwt(jwt -> jwt
//                                .jwtAuthenticationConverter(grantedAuthoritiesExtractor())
//                        )
//                )
//                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
//                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
//                .build();
//    }
//
//    @Bean
//    public ReactiveJwtDecoder jwtDecoder() {
//        return ReactiveJwtDecoders.fromIssuerLocation(issuerUri);
//    }
//
//    private ReactiveJwtAuthenticationConverterAdapter grantedAuthoritiesExtractor() {
//        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
//        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new GrantedAuthoritiesExtractor());
//        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
//    }
//
//    // Custom GrantedAuthoritiesExtractor to extract authorities from 'permissionKey' or other claims
//    static class GrantedAuthoritiesExtractor implements Converter<Jwt, Collection<GrantedAuthority>> {
//        public Collection<GrantedAuthority> convert(Jwt jwt) {
//            List<String> authorities = new ArrayList<>();
//
//            // Extract from 'permissionKey' which seems to be like [{role=ROLE_user.ban}]
//            // Assuming permissionKey is a list of maps, e.g., [{"role": "ROLE_user.ban"}]
//            List<Map<String, String>> permissionKeys = (List<Map<String, String>>) jwt.getClaims().getOrDefault("permissionKey", Collections.emptyList());
//            for (Map<String, String> perm : permissionKeys) {
//                String role = perm.get("role");
//                if (role != null) {
//                    authorities.add(role); // Add as is, since hasAuthority will check without prefix if custom
//                }
//            }
//
//            // If scope exists, add them as SCOPE_ prefixed
//            List<String> scopes = (List<String>) jwt.getClaims().getOrDefault("scope", Collections.emptyList());
//            for (String scope : scopes) {
//                authorities.add("SCOPE_" + scope);
//            }
//
//            return authorities.stream()
//                    .map(SimpleGrantedAuthority::new)
//                    .collect(Collectors.toList());
//        }
//    }
//}