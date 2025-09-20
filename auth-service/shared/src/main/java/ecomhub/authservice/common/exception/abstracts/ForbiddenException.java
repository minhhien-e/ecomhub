package ecomhub.authservice.common.exception.abstracts;

import ecomhub.authservice.common.enums.ErrorCode;

public class ForbiddenException extends HttpException {
    private static final String FORMAT = "You do not have sufficient permissions to %s.";

    public ForbiddenException(String message) {
        super(403, ErrorCode.FORBIDDEN, String.format(FORMAT, message));
    }
}
