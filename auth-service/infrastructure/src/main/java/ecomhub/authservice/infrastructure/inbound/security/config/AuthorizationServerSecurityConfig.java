package ecomhub.authservice.infrastructure.inbound.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import ecomhub.authservice.infrastructure.inbound.security.converter.DelegatingPublicClientTokenAuthenticationConverter;
import ecomhub.authservice.infrastructure.inbound.security.converter.PublicClientTokenRevocationAuthenticationConverter;
import ecomhub.authservice.infrastructure.inbound.security.exception.CustomAccessDeniedHandler;
import ecomhub.authservice.infrastructure.inbound.security.exception.CustomAuthenticationEntryPoint;
import ecomhub.authservice.infrastructure.inbound.security.exception.Oauth2AuthenticationFailureHandler;
import ecomhub.authservice.infrastructure.inbound.security.provider.DelegatingPublicClientTokenAuthenticationProvider;
import ecomhub.authservice.infrastructure.inbound.security.provider.PublicClientTokenRevocationAuthenticationProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

@EnableWebSecurity
@Configuration
public class AuthorizationServerSecurityConfig {

    private static final String[] PUBLIC_URLS = {
            "/oauth2/token",
            "/oauth2/jwks",
            "/.well-known/**",
            "/auth.css"
    };

    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerFilterChain(HttpSecurity http,
                                                              AuthorizationServerSettings authorizationServerSettings,
                                                              ObjectMapper objectMapper,
                                                              OAuth2TokenGenerator<OAuth2Token> tokenGenerator,
                                                              DelegatingPublicClientTokenAuthenticationProvider delegatingPublicClientTokenAuthenticationProvider,
                                                              PublicClientTokenRevocationAuthenticationProvider publicClientTokenRevocationAuthenticationProvider,
                                                              CustomAuthenticationEntryPoint customAuthenticationEntryPoint,
                                                              @Qualifier("corsConfigurationSource") CorsConfigurationSource configurationSource) throws Exception {
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
        Oauth2AuthenticationFailureHandler customAuthenticationFailureHandler = new Oauth2AuthenticationFailureHandler(objectMapper);
        CustomAccessDeniedHandler customAccessDeniedHandler = new CustomAccessDeniedHandler(objectMapper);

        // match cho Authorization Server endpoints
        http.securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_URLS).permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers(authorizationServerConfigurer.getEndpointsMatcher()))
                .cors(cors -> cors.configurationSource(configurationSource));

        // oauth2 config
        http.with(authorizationServerConfigurer, authorizationServer -> authorizationServer
                .authorizationServerSettings(authorizationServerSettings)
                .tokenEndpoint(tokenEndpointConfigurer -> tokenEndpointConfigurer
                        .authenticationProvider(delegatingPublicClientTokenAuthenticationProvider)
                        .accessTokenRequestConverter(new DelegatingPublicClientTokenAuthenticationConverter())
                        .errorResponseHandler(customAuthenticationFailureHandler)
                )
                .tokenRevocationEndpoint(tokenRevocationEndpointConfigurer -> tokenRevocationEndpointConfigurer
                        .authenticationProvider(publicClientTokenRevocationAuthenticationProvider)
                        .revocationRequestConverter(new PublicClientTokenRevocationAuthenticationConverter())
                        .errorResponseHandler(customAuthenticationFailureHandler)
                )
                .tokenGenerator(tokenGenerator)
                .oidc(oidc -> {
                })
        );

        // exception handling
        http.exceptionHandling(exception -> exception
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler)
        );

        return http.build();
    }
}


