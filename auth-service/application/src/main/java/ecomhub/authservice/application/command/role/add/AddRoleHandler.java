package ecomhub.authservice.application.command.role.add;

import ecomhub.authservice.application.command.interfaces.ICommandHandler;
import ecomhub.authservice.application.mapper.RoleCommandMapper;
import ecomhub.authservice.application.port.repository.RoleRepositoryPort;
import ecomhub.authservice.common.exception.concrete.role.conflict.RoleAlreadyExistsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddRoleHandler implements ICommandHandler<AddRoleCommand> {
    private final RoleRepositoryPort roleRepository;
    private final RoleCommandMapper roleCommandMapper;

    @Transactional
    @Override
    public void handle(AddRoleCommand command) {
        if (roleRepository.existsByName(command.getName())) {
            throw new RoleAlreadyExistsException(command.getName());
        }
        var role = roleCommandMapper.toDomain(command);
        command.getPermissionIds().forEach(role::grantPermission);
        roleRepository.save(role);
    }

}
