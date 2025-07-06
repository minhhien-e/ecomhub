package ecomhub.authservice.application.usecase.permission.impl;

import ecomhub.authservice.application.command.AddPermissionCommand;
import ecomhub.authservice.application.mapper.PermissionMapper;
import ecomhub.authservice.application.usecase.permission.AddPermissionUseCase;
import ecomhub.authservice.domain.service.permission.PermissionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddPermissionUseCaseImpl implements AddPermissionUseCase {
    private final PermissionService permissionService;
    private final PermissionMapper permissionMapper;
    @Transactional
    @Override
    public void execute(AddPermissionCommand command) {
        permissionService.addPermission(permissionMapper.toDomain(command));
    }
}
