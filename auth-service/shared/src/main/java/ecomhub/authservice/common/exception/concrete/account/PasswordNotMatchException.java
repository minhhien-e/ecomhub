package ecomhub.authservice.common.exception.concrete.account;

import ecomhub.authservice.common.exception.abstracts.BusinessRuleViolationException;

public class PasswordNotMatchException extends BusinessRuleViolationException {
    public PasswordNotMatchException() {
        super("The password and confirm password must match");
    }
}
