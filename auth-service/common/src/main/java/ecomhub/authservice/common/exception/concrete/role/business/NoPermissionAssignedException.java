package ecomhub.authservice.common.exception.concrete.role.business;

import ecomhub.authservice.common.exception.business.BusinessRuleViolationException;

public class NoPermissionAssignedException extends BusinessRuleViolationException {
    public NoPermissionAssignedException() {
        super("Vai trò phải có ít nhất một quyền.");
    }
}
