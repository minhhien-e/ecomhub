package ecomhub.authservice.application.usecase.permission.addpermission.impl;

import ecomhub.authservice.application.mapper.PermissionCommandMapper;
import ecomhub.authservice.application.usecase.permission.addpermission.AddPermissionCommand;
import ecomhub.authservice.application.ports.repositories.PermissionRepository;
import ecomhub.authservice.application.usecase.permission.addpermission.AddPermissionHandler;
import ecomhub.authservice.common.exception.PermissionAlreadyExistsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddPermissionHandlerImpl implements AddPermissionHandler {
    private final PermissionRepository permissionRepository;
    private final PermissionCommandMapper permissionCommandMapper;

    @Transactional
    @Override
    public void execute(AddPermissionCommand command) {
        if (permissionRepository.existsByName(command.name())) {
            throw new PermissionAlreadyExistsException();
        }
        permissionRepository.save(permissionCommandMapper.toDomain(command));
    }
}
