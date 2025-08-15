package ecomhub.authservice.application.command.role.permission.revoke;

import ecomhub.authservice.application.command.abstracts.ICommandHandler;
import ecomhub.authservice.common.exception.abstracts.ForbiddenException;
import ecomhub.authservice.common.exception.concrete.account.AccountNotFoundException;
import ecomhub.authservice.common.exception.concrete.permission.PermissionNotFoundException;
import ecomhub.authservice.common.exception.concrete.role.RoleNotFoundException;
import ecomhub.authservice.domain.repository.AccountRepositoryPort;
import ecomhub.authservice.domain.repository.PermissionRepositoryPort;
import ecomhub.authservice.domain.repository.RoleRepositoryPort;
import ecomhub.authservice.domain.service.abstracts.RoleService;
import ecomhub.authservice.domain.service.impl.RoleServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class RevokePermissionHandler implements ICommandHandler<RevokePermissionCommand> {
    private final RoleRepositoryPort roleRepository;
    private final PermissionRepositoryPort permissionRepository;
    private final AccountRepositoryPort accountRepository;
    private final RoleService roleService;

    public RevokePermissionHandler(RoleRepositoryPort roleRepository, PermissionRepositoryPort permissionRepository, AccountRepositoryPort accountRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.accountRepository = accountRepository;
        this.roleService = new RoleServiceImpl();
    }

    @Override
    public void handle(RevokePermissionCommand command) {
        var permission = permissionRepository.findByKey(command.getPermissionKey())
                .orElseThrow(() -> new PermissionNotFoundException(command.getPermissionKey()));
        var roleTarget = roleRepository.findById(command.getRoleId())
                .orElseThrow(() -> new RoleNotFoundException(command.getRoleId()));
        var requester = accountRepository.findById(command.getRequesterId())
                .orElseThrow(() -> new AccountNotFoundException(command.getRequesterId()));
        if (!roleService.canBeGrantPermissionBy(roleTarget, requester))
            throw new ForbiddenException("gán quyền cho vai trò");
        roleTarget.grantPermission(permission);
        roleRepository.revokePermissions(roleTarget.getId(), permission.getId());
    }
}
