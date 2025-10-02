package ecomhub.productservice.config.security.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Chuyển đổi JWT claim "authorities" -> GrantedAuthority
 * Để Spring Security hiểu user có những quyền gì.
 */
@Component // Spring tự scan bean này để inject vào SecurityConfig
public class CustomReactiveJwtAuthenticationConverter
        implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {

    @Override
    public Mono<AbstractAuthenticationToken> convert(Jwt jwt) {
        // Lấy claim "authorities" từ JWT (ví dụ: ["ROLE_USER", "ROLE_ADMIN"])
        List<String> authorities = jwt.getClaimAsStringList("authorities");
        if (authorities == null) authorities = List.of();

        // Map mỗi string -> GrantedAuthority
        Collection<GrantedAuthority> grantedAuthorities = authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // Trả về JwtAuthenticationToken để Spring quản lý trong SecurityContext
        return Mono.just(new JwtAuthenticationToken(jwt, grantedAuthorities));
    }
}
