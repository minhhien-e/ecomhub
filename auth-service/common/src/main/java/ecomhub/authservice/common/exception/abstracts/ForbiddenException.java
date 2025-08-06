package ecomhub.authservice.common.exception.abstracts;

import ecomhub.authservice.common.enums.ErrorCode;

public class ForbiddenException extends HttpException {
    private static final String format = "Bạn không có đủ quyền hạn để %s";

    public ForbiddenException(String message) {
        super(403, ErrorCode.FORBIDDEN, String.format(format, message));
    }
}
