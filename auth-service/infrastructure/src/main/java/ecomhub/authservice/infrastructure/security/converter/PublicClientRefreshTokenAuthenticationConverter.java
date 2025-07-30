package ecomhub.authservice.infrastructure.security.converter;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2RefreshTokenAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.MultiValueMap;

import static ecomhub.authservice.infrastructure.security.utils.OAuth2RequestParameterExtractor.getParameters;
import static ecomhub.authservice.infrastructure.security.utils.OAuth2RequestParameterExtractor.getValue;
import static ecomhub.authservice.infrastructure.security.utils.PublicClientAuthenticationFactory.createClientAuthentication;

public class PublicClientRefreshTokenAuthenticationConverter implements AuthenticationConverter {
    @Override
    public Authentication convert(HttpServletRequest request) {
        String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
        if (!AuthorizationGrantType.REFRESH_TOKEN.getValue().equals(grantType))
            return null;
        if (!"POST".equalsIgnoreCase(request.getMethod()) ||
                !request.getContentType().startsWith("application/x-www-form-urlencoded")) {
            return null;
        }
        MultiValueMap<String, String> parameters = getParameters(request);
        String clientId = getValue(parameters, OAuth2ParameterNames.CLIENT_ID, "Thông tin client Id không hợp lệ");
        String refreshToken = getValue(parameters, OAuth2ParameterNames.REFRESH_TOKEN, "Thông tin refresh token không hợp lệ");
        return new OAuth2RefreshTokenAuthenticationToken(refreshToken, createClientAuthentication(clientId), null, null);
    }
}
