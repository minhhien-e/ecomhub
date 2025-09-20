package ecomhub.authservice.application.command.role.permission.abstracts;

import ecomhub.authservice.common.utils.TriConsumer;
import ecomhub.authservice.domain.entity.Account;
import ecomhub.authservice.domain.entity.Permission;
import ecomhub.authservice.domain.entity.Role;
import ecomhub.authservice.domain.repository.AccountRepositoryPort;
import ecomhub.authservice.domain.repository.PermissionRepositoryPort;
import ecomhub.authservice.domain.repository.RoleRepositoryPort;
import ecomhub.authservice.domain.service.abstracts.RoleService;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public abstract class AbstractPermissionManagementHandler<TCommand>{
    protected final AccountRepositoryPort accountRepository;
    protected final PermissionRepositoryPort permissionRepository;
    protected final RoleRepositoryPort roleRepository;
    protected final RoleService roleService;

    public AbstractPermissionManagementHandler(AccountRepositoryPort accountRepository, PermissionRepositoryPort permissionRepository, RoleRepositoryPort roleRepository, RoleService roleService) {
        this.accountRepository = accountRepository;
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.roleService = roleService;
    }

    protected abstract void saveChange(Role role, Set<Permission> permissions);

    protected void handle(TCommand command, UUID roleId, UUID requesterId, TriConsumer<Role, Permission, Account> action) {
        var permissions = getPermissions(command);
        var roleTarget = roleRepository.getById(roleId);
        var requester = accountRepository.geById(requesterId);
        permissions.forEach(permission -> action.accept(roleTarget, permission, requester));
        saveChange(roleTarget, new HashSet<>(permissions));
    }
    protected abstract Set<Permission> getPermissions(TCommand command);
}
