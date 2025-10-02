package ecomhub.productservice.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration // class cấu hình Spring
public class ReactiveCorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // Cho phép tất cả origin (có thể giới hạn domain cụ thể trong production)
        config.setAllowedOriginPatterns(List.of("*"));
        // Các HTTP method được phép gọi
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        // Các header được phép gửi
        config.setAllowedHeaders(List.of("*"));
        // Cho phép gửi cookie/authorization header
        config.setAllowCredentials(true);

        // Apply cấu hình cho toàn bộ path /**
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
