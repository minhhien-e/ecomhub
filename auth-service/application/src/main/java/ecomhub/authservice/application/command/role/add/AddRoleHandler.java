package ecomhub.authservice.application.command.role.add;

import ecomhub.authservice.application.command.abstracts.ICommandHandler;
import ecomhub.authservice.application.mapper.RoleMapper;
import ecomhub.authservice.domain.repository.PermissionRepositoryPort;
import ecomhub.authservice.domain.repository.RoleRepositoryPort;
import ecomhub.authservice.common.exception.concrete.role.RoleAlreadyExistsException;
import ecomhub.authservice.domain.entity.Permission;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AddRoleHandler implements ICommandHandler<AddRoleCommand> {
    private final RoleRepositoryPort roleRepository;
    private final PermissionRepositoryPort permissionRepository;

    @Transactional
    @Override
    public void handle(AddRoleCommand command) {
        if (roleRepository.existsByName(command.getName())) {
            throw new RoleAlreadyExistsException(command.getName());
        }
        var role = RoleMapper.toDomain(command);
        var permissions = getPermissions(command.getPermissionKeys());
        permissions.forEach(role::grantPermission);
        roleRepository.save(role);
    }

    private Set<Permission> getPermissions(Set<String> permissionKeys) {
        return new HashSet<>(permissionRepository.findAllByKeyIn(permissionKeys));
    }

}
