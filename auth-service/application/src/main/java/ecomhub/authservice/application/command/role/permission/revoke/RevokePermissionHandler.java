package ecomhub.authservice.application.command.role.permission.revoke;

import ecomhub.authservice.application.command.abstracts.ICommandHandler;
import ecomhub.authservice.application.command.role.permission.abstracts.AbstractPermissionManagementHandler;
import ecomhub.authservice.domain.entity.Permission;
import ecomhub.authservice.domain.entity.Role;
import ecomhub.authservice.domain.repository.AccountRepositoryPort;
import ecomhub.authservice.domain.repository.PermissionRepositoryPort;
import ecomhub.authservice.domain.repository.RoleRepositoryPort;
import ecomhub.authservice.domain.service.abstracts.RoleService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Transactional
public class RevokePermissionHandler extends AbstractPermissionManagementHandler<RevokePermissionCommand> implements ICommandHandler<RevokePermissionCommand> {

    public RevokePermissionHandler(AccountRepositoryPort accountRepository, PermissionRepositoryPort permissionRepository, RoleRepositoryPort roleRepository, RoleService roleService) {
        super(accountRepository, permissionRepository, roleRepository, roleService);
    }

    @Override
    public void handle(RevokePermissionCommand command) {
        super.handle(command,command.getRoleId(),command.getRequesterId(),
                roleService::revokePermission);
    }

    @Override
    protected void saveChange(Role role, Set<Permission> permissions) {
        roleRepository.revokePermissions(role,permissions);
    }

    @Override
    protected Set<Permission> getPermissions(RevokePermissionCommand command) {
        return Set.of(permissionRepository.getById(command.getPermissionId()));
    }
}
