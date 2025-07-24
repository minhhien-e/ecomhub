package ecomhub.authservice.application.command.role.add;

import ecomhub.authservice.application.command.abstracts.ICommandHandler;
import ecomhub.authservice.application.mapper.RoleCommandMapper;
import ecomhub.authservice.application.port.repository.PermissionRepositoryPort;
import ecomhub.authservice.application.port.repository.RoleRepositoryPort;
import ecomhub.authservice.common.exception.concrete.role.RoleAlreadyExistsException;
import ecomhub.authservice.domain.entity.Permission;
import ecomhub.authservice.domain.entity.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddRoleHandler implements ICommandHandler<AddRoleCommand> {
    private final RoleRepositoryPort roleRepository;
    private final PermissionRepositoryPort permissionRepository;
    private final RoleCommandMapper roleCommandMapper;

    @Transactional
    @Override
    public void handle(AddRoleCommand command) {
        if (roleRepository.existsByName(command.getName())) {
            throw new RoleAlreadyExistsException(command.getName());
        }
        var role = roleCommandMapper.toDomain(command);
        var permissions = getPermissions(command.getPermissionKeys());
        permissions.forEach(role::grantPermission);
        roleRepository.save(role);
    }

    private Set<Permission> getPermissions(List<String> permissionKeys) {
        return new HashSet<>(permissionRepository.findAllByKeyIn(permissionKeys));
    }

}
