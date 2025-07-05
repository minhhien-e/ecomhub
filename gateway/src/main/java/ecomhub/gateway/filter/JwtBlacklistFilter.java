package ecomhub.gateway.filter;

import ecomhub.gateway.service.BlacklistTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtBlacklistFilter implements GlobalFilter {
    private final BlacklistTokenService blacklistTokenService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return ReactiveSecurityContextHolder.getContext()
                .flatMap(this::extractJti)
                .flatMap(jti -> blacklistTokenService.isBlacklisted(jti)
                        .flatMap(isBlacklisted -> {
                            if (isBlacklisted) {
                                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                                return exchange.getResponse().setComplete();
                            }
                            return chain.filter(exchange);
                        })
                )
                .switchIfEmpty(chain.filter(exchange));

    }

    private Mono<String> extractJti(SecurityContext context) {
        var auth = context.getAuthentication();
        if (auth instanceof JwtAuthenticationToken jwtAuth) {
            return Mono.justOrEmpty(jwtAuth.getToken().getId());
        }
        return Mono.empty();
    }

}
