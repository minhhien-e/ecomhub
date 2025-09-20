package ecomhub.authservice.common.exception.abstracts;

import ecomhub.authservice.common.enums.ErrorCode;

public class UpdateFailureException extends HttpException {
    public UpdateFailureException() {
        super(400, ErrorCode.UPDATE_FAILURE, "An error occurred. The information could not be updated.");
    }
}
