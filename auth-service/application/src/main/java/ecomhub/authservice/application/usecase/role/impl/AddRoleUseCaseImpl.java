package ecomhub.authservice.application.usecase.role.impl;

import ecomhub.authservice.application.command.AddRoleCommand;
import ecomhub.authservice.application.mapper.RoleMapper;
import ecomhub.authservice.application.usecase.role.AddRoleUseCase;
import ecomhub.authservice.domain.service.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddRoleUseCaseImpl implements AddRoleUseCase {
    private final RoleService roleService;
    private final RoleMapper roleMapper;

    @Override
    @Transactional
    public void execute(AddRoleCommand command) {
        roleService.addRole(roleMapper.toDomain(command));
    }
}
