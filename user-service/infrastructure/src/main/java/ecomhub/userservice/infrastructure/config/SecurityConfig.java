package ecomhub.userservice.infrastructure.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CorsConfiguration corsConfiguration;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Authorize endpoint
        http.
                authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests.anyRequest().authenticated());
        // Cors
        http.cors(cors -> cors.configurationSource(request-> corsConfiguration));

        return http.build();
    }
}
