package ecomhub.authservice.infrastructure.inbound.security.config;

import ecomhub.authservice.infrastructure.inbound.security.exception.FormAuthenticationFailureHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class LoginSecurityConfig {
    private static final String[] LOGIN_URLS = {
            "/auth/**",
            "/auth.css",
            "/auth",
    };

    @Order(2)
    @Bean
    public SecurityFilterChain loginFilterChain(HttpSecurity http,
                                                UserDetailsService userDetailsService,
                                                DaoAuthenticationProvider daoAuthenticationProvider) throws Exception {
        http.securityMatcher(LOGIN_URLS)
                .authorizeHttpRequests(request -> request.requestMatchers(LOGIN_URLS).permitAll())
                .userDetailsService(userDetailsService)
                .authenticationProvider(daoAuthenticationProvider);
        // login
        http.formLogin(login -> login
                .loginProcessingUrl("/auth/login")
                .failureHandler(new FormAuthenticationFailureHandler())
                .permitAll()
        );
        return http.build();
    }
}
