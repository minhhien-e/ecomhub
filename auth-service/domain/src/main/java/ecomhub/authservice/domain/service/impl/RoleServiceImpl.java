package ecomhub.authservice.domain.service.impl;

import ecomhub.authservice.domain.entity.Account;
import ecomhub.authservice.domain.entity.Role;
import ecomhub.authservice.domain.service.abstracts.RoleService;

public class RoleServiceImpl implements RoleService {

    @Override
    public boolean canBeModifiedBy(Role targetRole, Account requester) {
        return canPerformAction(targetRole, requester, "role.edit");
    }

    @Override
    public boolean canBeDeletedBy(Role targetRole, Account requester) {
        return canPerformAction(targetRole, requester, "role.delete");
    }

    //Kiểm tra có thực thi một quyền cụ thể
    private boolean canPerformAction(Role targetRole, Account requester, String permissionKey) {
        return requester.getRoles().stream().anyMatch(role ->
                role.getLevel().greaterThan(targetRole.getLevel())
                        && role.hasPermission(permissionKey)
        );
    }
}
