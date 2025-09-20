package ecomhub.authservice.application.command.permission.update.abstracts;

import ecomhub.authservice.common.exception.abstracts.UpdateFailureException;
import ecomhub.authservice.domain.entity.Permission;
import ecomhub.authservice.domain.repository.AccountRepositoryPort;
import ecomhub.authservice.domain.repository.PermissionRepositoryPort;
import ecomhub.authservice.domain.service.abstracts.PermissionService;

import java.util.UUID;
import java.util.function.BiConsumer;

public abstract class AbstractPermissionUpdateHandler<TValue> {
    protected final PermissionRepositoryPort permissionRepository;
    protected final PermissionService permissionService;

    protected AbstractPermissionUpdateHandler(PermissionRepositoryPort permissionRepository, PermissionService permissionService) {
        this.permissionRepository = permissionRepository;
        this.permissionService = permissionService;
    }

    protected void updateWithPermissionCheck(TValue value, UUID permissionId, BiConsumer<Permission, TValue> updateAction) {
        var permission = permissionRepository.getById(permissionId);
        updateAction.accept(permission, value);
        int result = saveChange(permission);
        if (result == 0) throw new UpdateFailureException();
    }

    protected abstract int saveChange(Permission targetPermission);

}
