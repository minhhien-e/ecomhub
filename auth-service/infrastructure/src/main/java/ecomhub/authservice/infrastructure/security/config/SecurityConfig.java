package ecomhub.authservice.infrastructure.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private static final String[] PUBLIC_URLS = {"/actuator/**", "/login", "/error", "/favicon.ico", "/api/v*/auth/register", "/perform_login", "/api/v*/auth/role", "/api/v*/auth/permission"};

    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        author -> author
                                .requestMatchers(PUBLIC_URLS)
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .build();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerFilterChain(HttpSecurity http, UserDetailsService userDetailsService, AuthorizationServerSettings authorizationServerSettings) throws Exception {
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
                new OAuth2AuthorizationServerConfigurer();
        http
                .securityMatcher(new OrRequestMatcher(
                        authorizationServerConfigurer.getEndpointsMatcher(),
                        request -> request.getRequestURI().startsWith("/login")
                ))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/login", "/error", "/favicon.ico").permitAll()
                                .anyRequest().authenticated())
                .formLogin(login -> login.loginPage("/login")
                        .successHandler(new SavedRequestAwareAuthenticationSuccessHandler())
                        .permitAll())

                .csrf(csrf -> csrf.ignoringRequestMatchers(authorizationServerConfigurer.getEndpointsMatcher()))
                .userDetailsService(userDetailsService)
                .with(authorizationServerConfigurer, config ->
                        config.authorizationServerSettings(authorizationServerSettings)
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
