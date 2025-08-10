package ecomhub.authservice.common.exception.abstracts;

import ecomhub.authservice.common.enums.ErrorCode;

public class BusinessRuleViolationException extends HttpException {
    public BusinessRuleViolationException(String message) {
        super(400, ErrorCode.BUSINESS_RULE_VIOLATION, message);
    }
}
