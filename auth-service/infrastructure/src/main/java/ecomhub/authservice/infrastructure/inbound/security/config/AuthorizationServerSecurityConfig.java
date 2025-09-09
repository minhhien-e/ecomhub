package ecomhub.authservice.infrastructure.inbound.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import ecomhub.authservice.infrastructure.inbound.security.converter.DelegatingPublicClientTokenAuthenticationConverter;
import ecomhub.authservice.infrastructure.inbound.security.converter.PublicClientTokenRevocationAuthenticationConverter;
import ecomhub.authservice.infrastructure.inbound.security.exception.CustomAccessDeniedHandler;
import ecomhub.authservice.infrastructure.inbound.security.exception.CustomAuthenticationEntryPoint;
import ecomhub.authservice.infrastructure.inbound.security.exception.FormAuthenticationFailureHandler;
import ecomhub.authservice.infrastructure.inbound.security.exception.Oauth2AuthenticationFailureHandler;
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
    private static final String[] PUBLIC_AUTH_URLS = {"/oauth2/token", "/oauth2/revoke", "/oauth2/jwts"};
    private static final String[] AUTH_URLS = {"/auth/**", "/auth", "/auth.css"};

    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerFilterChain(HttpSecurity http,
                                                              UserDetailsService userDetailsService,
                                                              AuthorizationServerSettings authorizationServerSettings,
                                                              ObjectMapper objectMapper,
                                                              OAuth2TokenGenerator<OAuth2Token> tokenGenerator,
                                                              DelegatingPublicClientTokenAuthenticationProvider delegatingPublicClientTokenAuthenticationProvider,
                                                              DaoAuthenticationProvider daoAuthenticationProvider,
                                                              PublicClientTokenRevocationAuthenticationProvider publicClientTokenRevocationAuthenticationProvider,
                                                              CustomAuthenticationEntryPoint customAuthenticationEntryPoint, @Qualifier("corsConfigurationSource") CorsConfigurationSource configurationSource) throws Exception {
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
        Oauth2AuthenticationFailureHandler customAuthenticationFailureHandler = new Oauth2AuthenticationFailureHandler(objectMapper);
        CustomAccessDeniedHandler customAccessDeniedHandler = new CustomAccessDeniedHandler(objectMapper);

        //match request
        http.securityMatcher(new OrRequestMatcher(
                authorizationServerConfigurer.getEndpointsMatcher(),
                request -> Arrays.stream(AUTH_URLS)
                        .anyMatch(uri -> request.getRequestURI().startsWith(uri))
        ));
        //authorize, cors and csrf
        http.authorizeHttpRequests(auth ->
                        auth.requestMatchers(PUBLIC_AUTH_URLS).permitAll()
                                .requestMatchers(AUTH_URLS).permitAll()
                                .anyRequest().authenticated())
                .csrf(csrf -> csrf.ignoringRequestMatchers(authorizationServerConfigurer.getEndpointsMatcher()))
                .cors(cors -> cors.configurationSource(configurationSource));
        // oauth2
        http.userDetailsService(userDetailsService)
                .with(authorizationServerConfigurer, authorizationServer ->
                        authorizationServer
                                .authorizationServerSettings(authorizationServerSettings)
                                //token
                                .tokenEndpoint(tokenEndpointConfigurer ->
                                        tokenEndpointConfigurer
                                                .authenticationProvider(delegatingPublicClientTokenAuthenticationProvider)
                                                .accessTokenRequestConverter(new DelegatingPublicClientTokenAuthenticationConverter())
                                                .errorResponseHandler(customAuthenticationFailureHandler)
                                )
                                //revoke token
                                .tokenRevocationEndpoint(tokenRevocationEndpointConfigurer ->
                                        tokenRevocationEndpointConfigurer
                                                .authenticationProvider(publicClientTokenRevocationAuthenticationProvider)
                                                .revocationRequestConverter(new PublicClientTokenRevocationAuthenticationConverter())
                                                .errorResponseHandler(customAuthenticationFailureHandler)
                                )
                                //token generator
                                .tokenGenerator(tokenGenerator)
                ).authenticationProvider(daoAuthenticationProvider);
        //exception handling
        http.exceptionHandling(exception ->
                exception.authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler)
        );
        //login
        http.formLogin(login -> login
                .loginProcessingUrl("/auth/login")
                .failureHandler(new FormAuthenticationFailureHandler())
                .permitAll());
        return http.build();
    }
}

