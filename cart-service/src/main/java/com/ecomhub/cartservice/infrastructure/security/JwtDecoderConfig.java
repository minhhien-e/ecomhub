//package com.ecomhub.cartservice.infrastructure.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
//
//@Configuration
//public class JwtDecoderConfig {
//
//    @Bean
//    public JwtDecoder jwtDecoder() {
//        String jwkSetUri = "http://localhost:8081/oauth2/jwks";
//        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
//    }
//}
