package ecomhub.authservice.common.exception.concrete.account;

import ecomhub.authservice.common.exception.abstracts.BusinessRuleViolationException;

public class NoRoleAssignedException extends BusinessRuleViolationException {
    public NoRoleAssignedException() {
        super("The account must have at least one role assigned.");
    }
}
