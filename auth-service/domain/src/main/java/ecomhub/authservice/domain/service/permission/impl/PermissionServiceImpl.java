package ecomhub.authservice.domain.service.permission.impl;

import ecomhub.authservice.common.exception.PermissionAlreadyExistsException;
import ecomhub.authservice.domain.model.Permission;
import ecomhub.authservice.domain.repository.PermissionRepository;
import ecomhub.authservice.domain.service.permission.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;

    @Override
    public void addPermission(Permission permission) {
        if (isPermissionExists(permission.getName())) {
            throw new PermissionAlreadyExistsException();
        }
        permissionRepository.save(permission);
    }

    @Override
    public boolean isPermissionExists(String name) {
        return permissionRepository.existsByName(name);
    }
}
