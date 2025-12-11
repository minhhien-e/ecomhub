package ecomhub.authservice.infrastructure.inbound.security.converter;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2TokenRevocationAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.MultiValueMap;

import static ecomhub.authservice.infrastructure.inbound.security.utils.OAuth2RequestParameterExtractor.getParameters;
import static ecomhub.authservice.infrastructure.inbound.security.utils.OAuth2RequestParameterExtractor.getValue;
import static ecomhub.authservice.infrastructure.inbound.security.utils.PublicClientAuthenticationFactory.createClientAuthentication;

public class PublicClientTokenRevocationAuthenticationConverter implements AuthenticationConverter {
    @Override
    public Authentication convert(HttpServletRequest request) {
        MultiValueMap<String, String> params = getParameters(request);
        String token = getValue(params, OAuth2ParameterNames.TOKEN, "Thông tin token không hợp lệ");
        String tokenTypeHint = getValue(params, OAuth2ParameterNames.TOKEN_TYPE_HINT, "Thông tin token hint không hợp lệ");
        String clientId = getValue(params, OAuth2ParameterNames.CLIENT_ID, "Thông tin client Id không hợp lệ");
        return new OAuth2TokenRevocationAuthenticationToken(token, createClientAuthentication(clientId), tokenTypeHint);
    }
}
