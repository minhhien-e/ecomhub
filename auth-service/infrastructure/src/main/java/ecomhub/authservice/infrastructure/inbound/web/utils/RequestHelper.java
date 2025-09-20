package ecomhub.authservice.infrastructure.inbound.web.utils;

import jakarta.servlet.http.HttpServletRequest;

public class RequestHelper {
    public static boolean isApiRequest(HttpServletRequest request) {
        String accept = request.getHeader("Accept");
        return accept != null && accept.contains("application/json");
    }
}
