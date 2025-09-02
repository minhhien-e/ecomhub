package ecomhub.authservice.infrastructure.inbound.security.config;

import ecomhub.authservice.infrastructure.inbound.security.converter.DelegatingPublicClientTokenAuthenticationConverter;
import ecomhub.authservice.infrastructure.inbound.security.converter.PublicClientTokenRevocationAuthenticationConverter;
import ecomhub.authservice.infrastructure.inbound.security.exception.CustomAccessDeniedHandler;
import ecomhub.authservice.infrastructure.inbound.security.exception.CustomAuthenticationEntryPoint;
import ecomhub.authservice.infrastructure.inbound.security.exception.CustomAuthenticationFailureHandler;
import ecomhub.authservice.infrastructure.inbound.security.provider.DelegatingPublicClientTokenAuthenticationProvider;
import ecomhub.authservice.infrastructure.inbound.security.provider.PublicClientTokenRevocationAuthenticationProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
@Configuration
public class AuthorizationServerSecurityConfig {
    private static final String[] PUBLIC_AUTH_URLS = {"/login", "/favicon.ico", "/oauth2/token", "/oauth2/revoke", "/oauth2/jwts"};

    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerFilterChain(HttpSecurity http,
                                                              UserDetailsService userDetailsService,
                                                              AuthorizationServerSettings authorizationServerSettings,
                                                              OAuth2TokenGenerator<OAuth2Token> tokenGenerator,
                                                              DelegatingPublicClientTokenAuthenticationProvider delegatingPublicClientTokenAuthenticationProvider,
                                                              DaoAuthenticationProvider daoAuthenticationProvider,
                                                              PublicClientTokenRevocationAuthenticationProvider publicClientTokenRevocationAuthenticationProvider,
                                                              CustomAccessDeniedHandler customAccessDeniedHandler,
                                                              CustomAuthenticationEntryPoint customAuthenticationEntryPoint,
                                                              CustomAuthenticationFailureHandler customAuthenticationFailureHandler
            , @Qualifier("corsConfigurationSource") CorsConfigurationSource configurationSource) throws Exception {
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
        http
                .securityMatcher(new OrRequestMatcher(
                        authorizationServerConfigurer.getEndpointsMatcher(),
                        request -> Arrays.stream(PUBLIC_AUTH_URLS)
                                .anyMatch(uri -> request.getRequestURI().startsWith(uri))
                ))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(PUBLIC_AUTH_URLS).permitAll()
                                .anyRequest().authenticated())
                .formLogin(login -> login.loginPage("/login")
                        .failureHandler(customAuthenticationFailureHandler)
                        .permitAll())
                .csrf(csrf -> csrf.ignoringRequestMatchers(authorizationServerConfigurer.getEndpointsMatcher()))
                .cors(cors -> cors.configurationSource(configurationSource))
                .userDetailsService(userDetailsService)
                .with(authorizationServerConfigurer, authorizationServer ->
                        authorizationServer
                                .authorizationServerSettings(authorizationServerSettings)
                                .tokenEndpoint(tokenEndpointConfigurer ->
                                        tokenEndpointConfigurer
                                                .authenticationProvider(delegatingPublicClientTokenAuthenticationProvider)
                                                .accessTokenRequestConverter(new DelegatingPublicClientTokenAuthenticationConverter())
                                                .errorResponseHandler(customAuthenticationFailureHandler)
                                )
                                .tokenRevocationEndpoint(tokenRevocationEndpointConfigurer ->
                                        tokenRevocationEndpointConfigurer
                                                .authenticationProvider(publicClientTokenRevocationAuthenticationProvider)
                                                .revocationRequestConverter(new PublicClientTokenRevocationAuthenticationConverter())
                                                .errorResponseHandler(customAuthenticationFailureHandler)
                                )
                                .tokenGenerator(tokenGenerator)
                ).authenticationProvider(daoAuthenticationProvider)
                .exceptionHandling(exception ->
                        exception.authenticationEntryPoint(customAuthenticationEntryPoint)
                                .accessDeniedHandler(customAccessDeniedHandler)
                );
        return http.build();
    }
}

