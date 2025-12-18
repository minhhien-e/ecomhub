package com.feature.cartservice.converter;

import com.feature.cartservice.model.CustomUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Component
@Slf4j
public class CustomJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        log.info("JWT claims: {}", jwt.getClaims());

        UUID userId = UUID.fromString(jwt.getSubject());
        log.info(userId.toString());
        String username = jwt.getClaimAsString("preferred_username");

        List<String> roles = jwt.getClaimAsStringList("roles");
        if (roles == null) roles = jwt.getClaimAsStringList("authorities");
        if (roles == null) roles = List.of();

        log.info("Extracted roles: {}", roles);

        Collection<GrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        log.info("Mapped authorities: {}", authorities);

        CustomUser user = new CustomUser(userId, username, authorities);
        log.info("Authenticated user: {} with id: {}", username, userId);

        return new JwtAuthenticationToken(jwt, authorities, userId.toString()) {
            @Override
            public Object getPrincipal() {
                return user;
            }
        };
    }
}
