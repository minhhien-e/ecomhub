//package com.ecomhub.cartservice.infrastructure.security;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.oauth2.jwt.JwtException;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Component
//@RequiredArgsConstructor
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    private final JwtDecoder jwtDecoder;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain)
//            throws ServletException, IOException {
//
//        String token = resolveToken(request);
//        if (token != null) {
//            try {
//                Jwt jwt = jwtDecoder.decode(token);
//
//                String username = jwt.getClaimAsString("sub");
////                String userId = jwt.getClaimAsString("userId");
//                List<String> roles = jwt.getClaimAsStringList("roles");
//
//                var authorities = roles.stream()
//                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
//                        .collect(Collectors.toList());
//
//                var authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            } catch (JwtException e) {
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                response.setContentType("application/json");
//                response.getWriter().write("{\"error\": \"Invalid or expired token\"}");
//                return;
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//    private String resolveToken(HttpServletRequest request) {
//        String bearerToken = request.getHeader("Authorization");
//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7);
//        }
//        return null;
//    }
//}
