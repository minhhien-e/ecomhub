package ecomhub.authservice.common.exception.concrete.account.business;

import ecomhub.authservice.common.exception.business.BusinessRuleViolationException;

public class NoRoleAssignedException extends BusinessRuleViolationException {
    public NoRoleAssignedException() {
        super("Tài khoản phải có ít nhất một vai trò.");
    }
}
