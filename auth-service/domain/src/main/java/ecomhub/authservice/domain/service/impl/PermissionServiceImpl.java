package ecomhub.authservice.domain.service.impl;

import ecomhub.authservice.domain.entity.Account;
import ecomhub.authservice.domain.service.abstracts.PermissionService;

public class PermissionServiceImpl implements PermissionService {
    @Override
    public boolean canBeModifiedBy(Account requester) {
        return hasPermission(requester, "permission.edit");
    }

    @Override
    public boolean canBeDeletedBy(Account requester) {
        return hasPermission(requester, "permission.delete");
    }

    private boolean hasPermission(Account requester, String permissionKey) {
        return requester.getRoles().stream().anyMatch(role -> role.hasPermission(permissionKey));
    }
}
