package ecomhub.promotiondiscount.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.Arrays;
import java.util.stream.Collectors;

@Configuration
public class JwtAuthenticationConverterConfig {

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            String scope = jwt.getClaimAsString("authorities");
            if (scope == null || scope.isBlank()) {
                return java.util.Collections.emptyList();
            }

            // Map scope -> ROLE.{scope}, ví dụ read -> ROLE.read
            return Arrays.stream(scope.split("\\s+"))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        });

        return converter;
    }
}

