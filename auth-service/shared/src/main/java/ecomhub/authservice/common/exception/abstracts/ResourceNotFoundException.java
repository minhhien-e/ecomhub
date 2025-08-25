package ecomhub.authservice.common.exception.abstracts;

import ecomhub.authservice.common.enums.ErrorCode;

public class ResourceNotFoundException extends HttpException {
    private static final String format = "Sorry, we couldn't find the %s you are looking for.";

    public ResourceNotFoundException(String resourceName) {
        super(404, ErrorCode.RESOURCE_NOT_FOUND, String.format(format, resourceName));
    }
}
