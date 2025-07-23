package ecomhub.authservice.infrastructure.security.config;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import ecomhub.authservice.infrastructure.security.service.OAuth2PublicClientRefreshTokenGenerator;
import ecomhub.authservice.infrastructure.security.converter.PublicClientRefreshTokenAuthenticationConverter;
import ecomhub.authservice.infrastructure.security.provider.PublicClientRefreshTokenAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.DelegatingOAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.JwtGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

import java.util.Arrays;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private static final String[] PUBLIC_API_URLS = {"/api/v*/auth/register", "/actuator/**"};
    private static final String[] PUBLIC_AUTH_URLS = {"/login", "/favicon.ico", "/oauth2/token"};

    @Bean
    @Order(2)
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        author -> author
                                .requestMatchers(PUBLIC_API_URLS)
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerFilterChain(HttpSecurity http,
                                                              UserDetailsService userDetailsService,
                                                              AuthorizationServerSettings authorizationServerSettings,
                                                              OAuth2TokenGenerator<OAuth2Token> tokenGenerator,
                                                              PublicClientRefreshTokenAuthenticationProvider publicClientRefreshTokenAuthenticationProvider,
                                                              DaoAuthenticationProvider daoAuthenticationProvider
    ) throws Exception {
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
                        .permitAll())

                .csrf(csrf -> csrf.ignoringRequestMatchers(authorizationServerConfigurer.getEndpointsMatcher()))
                .cors(Customizer.withDefaults())
                .userDetailsService(userDetailsService)
                .with(authorizationServerConfigurer, authorizationServer ->
                        authorizationServer
                                .authorizationServerSettings(authorizationServerSettings)
                                .tokenEndpoint(tokenEndpointConfigurer ->
                                        tokenEndpointConfigurer
                                                .authenticationProvider(publicClientRefreshTokenAuthenticationProvider)
                                                .accessTokenRequestConverter(new PublicClientRefreshTokenAuthenticationConverter()))
                                .tokenGenerator(tokenGenerator)
                ).authenticationProvider(daoAuthenticationProvider);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    @Bean
    public OAuth2TokenGenerator<OAuth2Token> tokenGenerator(JWKSource<SecurityContext> jwkSource) {
        JwtEncoder jwtEncoder = new NimbusJwtEncoder(jwkSource);
        JwtGenerator jwtAccessTokenGenerator = new JwtGenerator(jwtEncoder);
        return new DelegatingOAuth2TokenGenerator(jwtAccessTokenGenerator,
                new OAuth2PublicClientRefreshTokenGenerator());
    }

}
