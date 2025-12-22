package ecomhub.authservice.infrastructure.inbound.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import ecomhub.authservice.infrastructure.inbound.security.exception.CustomAccessDeniedHandler;
import ecomhub.authservice.infrastructure.inbound.security.exception.CustomAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class ApiSecurityConfig {
    private static final String[] PUBLIC_API_URLS = {"/api/v1/auth/account/register", "/actuator/**"};

    @Bean
    @Order(3)
    public SecurityFilterChain apiFilterChain(HttpSecurity http,
                                              @Qualifier("corsConfigurationSource") CorsConfigurationSource configurationSource,
                                              JwtAuthenticationConverter converter,
                                              ObjectMapper objectMapper
    ) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(configurationSource)).csrf(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        author -> author
                                .requestMatchers(PUBLIC_API_URLS)
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwtConfigurer -> jwtConfigurer
                                .jwtAuthenticationConverter(converter)))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(handling -> handling
                        .accessDeniedHandler(new CustomAccessDeniedHandler(objectMapper))
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint(objectMapper)))
                .build();
    }
}
