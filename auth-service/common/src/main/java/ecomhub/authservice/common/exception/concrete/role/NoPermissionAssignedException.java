package ecomhub.authservice.common.exception.concrete.role;

import ecomhub.authservice.common.exception.abstracts.BusinessRuleViolationException;

public class NoPermissionAssignedException extends BusinessRuleViolationException {
    public NoPermissionAssignedException() {
        super("Vai trò phải có ít nhất một quyền.");
    }
}
