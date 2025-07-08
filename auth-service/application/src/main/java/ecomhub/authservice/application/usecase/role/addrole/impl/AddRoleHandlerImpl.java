package ecomhub.authservice.application.usecase.role.addrole.impl;

import ecomhub.authservice.application.usecase.role.addrole.AddRoleCommand;
import ecomhub.authservice.application.mapper.RoleCommandMapper;
import ecomhub.authservice.application.usecase.role.addrole.AddRoleHandler;
import ecomhub.authservice.common.exception.RoleAlreadyExistsException;
import ecomhub.authservice.application.ports.repositories.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddRoleHandlerImpl implements AddRoleHandler {
    private final RoleRepository roleRepository;
    private final RoleCommandMapper roleCommandMapper;

    @Override
    @Transactional
    public void execute(AddRoleCommand command) {
        if (roleRepository.existsByName(command.name())) {
            throw new RoleAlreadyExistsException();
        }
        roleRepository.save(roleCommandMapper.toDomain(command));
    }
}
