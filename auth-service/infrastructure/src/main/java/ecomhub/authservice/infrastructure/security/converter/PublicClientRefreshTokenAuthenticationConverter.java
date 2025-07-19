package ecomhub.authservice.infrastructure.security.converter;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2RefreshTokenAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;

public class PublicClientRefreshTokenAuthenticationConverter implements AuthenticationConverter {
    @Override
    public Authentication convert(HttpServletRequest request) {
        String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
        if (!AuthorizationGrantType.REFRESH_TOKEN.getValue().equals(grantType))
            return null;
        MultiValueMap<String, String> parameters = getParameters(request);
        String clientId = getClientId(parameters);
        String refreshToken = getRefreshToken(parameters);
        return new OAuth2RefreshTokenAuthenticationToken(refreshToken, createClientAuthentication(clientId), null, null);

    }

    private Authentication createClientAuthentication(String clientId) {
        return new OAuth2ClientAuthenticationToken(clientId, ClientAuthenticationMethod.NONE, null, Collections.emptyMap());
    }

    private String getRefreshToken(MultiValueMap<String, String> parameters) {
        String refreshToken = parameters.getFirst(OAuth2ParameterNames.REFRESH_TOKEN);
        if (!StringUtils.hasText(refreshToken) || parameters.get(OAuth2ParameterNames.REFRESH_TOKEN).size() != 1) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_REQUEST);
        }
        parameters.remove(OAuth2ParameterNames.REFRESH_TOKEN);
        return refreshToken;
    }

    private String getClientId(MultiValueMap<String, String> parameters) {
        String clientId = parameters.getFirst(OAuth2ParameterNames.CLIENT_ID);
        if (!StringUtils.hasText(clientId) || parameters.get(OAuth2ParameterNames.CLIENT_ID).size() != 1) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_REQUEST);
        }
        parameters.remove(OAuth2ParameterNames.CLIENT_ID);
        return clientId;
    }

    private MultiValueMap<String, String> getParameters(HttpServletRequest request) {
        UriComponents uri = UriComponentsBuilder
                .fromUriString(request.getRequestURL().toString() + "?" + request.getQueryString())
                .build();
        return uri.getQueryParams();
    }
}
