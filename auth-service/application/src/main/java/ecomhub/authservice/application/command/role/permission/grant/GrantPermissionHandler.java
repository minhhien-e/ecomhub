package ecomhub.authservice.application.command.role.permission.grant;

import ecomhub.authservice.application.command.abstracts.ICommandHandler;
import ecomhub.authservice.common.exception.abstracts.ForbiddenException;
import ecomhub.authservice.common.exception.concrete.account.AccountNotFoundException;
import ecomhub.authservice.common.exception.concrete.permission.PermissionNotFoundException;
import ecomhub.authservice.common.exception.concrete.role.RoleNotFoundException;
import ecomhub.authservice.domain.entity.Permission;
import ecomhub.authservice.domain.repository.AccountRepositoryPort;
import ecomhub.authservice.domain.repository.PermissionRepositoryPort;
import ecomhub.authservice.domain.repository.RoleRepositoryPort;
import ecomhub.authservice.domain.service.abstracts.RoleService;
import ecomhub.authservice.domain.service.impl.RoleServiceImpl;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class GrantPermissionHandler implements ICommandHandler<GrantPermissionCommand> {
    private final RoleRepositoryPort roleRepository;
    private final PermissionRepositoryPort permissionRepository;
    private final AccountRepositoryPort accountRepository;
    private final RoleService roleService;

    public GrantPermissionHandler(RoleRepositoryPort roleRepository, PermissionRepositoryPort permissionRepository, AccountRepositoryPort accountRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.accountRepository = accountRepository;
        this.roleService = new RoleServiceImpl();
    }

    @Override
    public void handle(GrantPermissionCommand command) {
        command.getPermissionKeys().forEach(key -> {
            if (!permissionRepository.existsByKey(key))
                throw new PermissionNotFoundException(key);
        });
        var roleTarget = roleRepository.findById(command.getRoleId())
                .orElseThrow(() -> new RoleNotFoundException(command.getRoleId()));
        var requester = accountRepository.findById(command.getRequesterId())
                .orElseThrow(() -> new AccountNotFoundException(command.getRequesterId()));
        if (!roleService.canBeGrantPermissionBy(roleTarget, requester))
            throw new ForbiddenException("gán quyền cho vai trò");
        var permissions = permissionRepository.findAllByKeyIn(command.getPermissionKeys());
        for (var permission : permissions) {
            roleTarget.grantPermission(permission);
        }
        roleRepository.grantPermissions(roleTarget.getId(), permissions.stream().map(Permission::getId).collect(Collectors.toSet()));
    }
}
