package ecomhub.authservice.application.command.role.permission.grant;

import ecomhub.authservice.application.command.abstracts.ICommandHandler;
import ecomhub.authservice.application.command.role.permission.abstracts.AbstractPermissionManagementHandler;
import ecomhub.authservice.common.exception.concrete.permission.PermissionNotFoundException;
import ecomhub.authservice.domain.entity.Permission;
import ecomhub.authservice.domain.entity.Role;
import ecomhub.authservice.domain.repository.AccountRepositoryPort;
import ecomhub.authservice.domain.repository.PermissionRepositoryPort;
import ecomhub.authservice.domain.repository.RoleRepositoryPort;
import ecomhub.authservice.domain.service.abstracts.RoleService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class GrantPermissionHandler extends AbstractPermissionManagementHandler<GrantPermissionCommand> implements ICommandHandler<GrantPermissionCommand> {

    public GrantPermissionHandler(AccountRepositoryPort accountRepository, PermissionRepositoryPort permissionRepository, RoleRepositoryPort roleRepository, RoleService roleService) {
        super(accountRepository, permissionRepository, roleRepository, roleService);
    }

    @Override
    protected void saveChange(Role role, Set<Permission> permissions) {
        roleRepository.grantPermissions(role, permissions);
    }

    @Override
    protected Set<Permission> getPermissions(GrantPermissionCommand command) {
        command.getPermissionKeys().forEach(key -> {
            if (!permissionRepository.existsByKey(key))
                throw new PermissionNotFoundException();
        });
        return new HashSet<>(permissionRepository.findAllByKeyIn(command.getPermissionKeys()));
    }

    @Override
    public void handle(GrantPermissionCommand command) {
        super.handle(command, command.getRoleId(), command.getRequesterId(),
                roleService::grantPermission);
    }

}
