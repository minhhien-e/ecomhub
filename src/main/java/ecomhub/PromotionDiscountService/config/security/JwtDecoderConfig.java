package ecomhub.PromotionDiscountService.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
public class JwtDecoderConfig {

    @Bean
    public JwtDecoder jwtDecoder() {
        // Cho test local: dùng key giả, ví dụ HMAC
        String secret = "01234567890123456789012345678901"; // 32 ký tự cho HS256
        return NimbusJwtDecoder.withSecretKey(new javax.crypto.spec.SecretKeySpec(
                secret.getBytes(), "HmacSHA256")).build();
    }
}
