package ecomhub.authservice.domain.service.impl;

import ecomhub.authservice.common.exception.abstracts.ForbiddenException;
import ecomhub.authservice.domain.entity.Account;
import ecomhub.authservice.domain.entity.Permission;
import ecomhub.authservice.domain.entity.Role;
import ecomhub.authservice.domain.service.abstracts.RoleService;
import ecomhub.authservice.domain.valueobject.Level;

import java.util.function.BiConsumer;

public class RoleServiceImpl implements RoleService {


    @Override
    public boolean canBeDeletedBy(Role targetRole, Account requester) {
        return canPerformAction(targetRole, requester, "role.delete");
    }

    @Override
    public void updateName(Role targetRole, Account requester, String newName) {
        update(targetRole, requester, "name", newName,
                (role, value) -> role.updateName((String) value));
    }

    @Override
    public void updateDescription(Role targetRole, Account requester, String newDescription) {
        update(targetRole, requester, "description", newDescription,
                (role, value) -> role.updateDescription((String) value));
    }

    @Override
    public void updateLevel(Role targetRole, Account requester, Integer newLevel) {
        if (requester.getHighestRole().getLevel().lessThan(new Level(newLevel)))
            update(targetRole, requester, "level", newLevel,
                    (role, value) -> role.updateLevel((Integer) value));
        else throw new ForbiddenException("update the level of the role");
    }

    @Override
    public void updateType(Role targetRole, Account requester, String newType) {
        update(targetRole, requester, "type", newType,
                (role, value) -> role.updateType((String) value));
    }

    private void update(Role targetRole, Account requester,
                        String fieldName,
                        Object value, BiConsumer<Role, Object> action) {
        if (canPerformAction(targetRole, requester, "role.add"))
            action.accept(targetRole, value);
        else throw new ForbiddenException("update the " + fieldName + " of the role");
    }

    @Override
    public void grantPermission(Role targetRole, Permission targetPermission, Account requester) {
        if (canPerformAction(targetRole, requester, "role.permission.grant"))
            targetRole.grantPermission(targetPermission);
        else throw new ForbiddenException("grant a permission to the role");
    }

    @Override
    public void revokePermission(Role targetRole, Permission targetPermission, Account requester) {
        if (canPerformAction(targetRole, requester, "role.permission.grant"))
            targetRole.revokePermission(targetPermission);
        else throw new ForbiddenException("revoke a permission from the role");
    }

    @Override
    public void delete(Role targetRole, Account requester, boolean isHard) {
        if (canPerformAction(targetRole, requester, "role.add")) {
            if (!isHard) targetRole.delete();
        } else throw new ForbiddenException("delete the role");
    }

    //Kiểm tra có thực thi một quyền cụ thể
    private boolean canPerformAction(Role targetRole, Account requester, String permissionKey) {
        return requester.getRoles().stream().anyMatch(role ->
                role.greaterThan(targetRole)
                        && role.hasPermission(permissionKey)
        );
    }
}
