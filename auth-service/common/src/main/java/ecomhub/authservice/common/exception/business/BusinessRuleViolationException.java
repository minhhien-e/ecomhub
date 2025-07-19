package ecomhub.authservice.common.exception.business;

import ecomhub.authservice.common.enums.ErrorCode;
import ecomhub.authservice.common.exception.HttpException;

public class BusinessRuleViolationException extends HttpException {
    public BusinessRuleViolationException(String message) {
        super(400, ErrorCode.BUSINESS_RULE_VIOLATION, message);
    }
}
