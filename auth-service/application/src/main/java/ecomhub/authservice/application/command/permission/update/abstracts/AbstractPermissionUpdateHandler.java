package ecomhub.authservice.application.command.permission.update.abstracts;

import ecomhub.authservice.common.exception.abstracts.ForbiddenException;
import ecomhub.authservice.common.exception.abstracts.UpdateFailureException;
import ecomhub.authservice.common.exception.concrete.account.AccountNotFoundException;
import ecomhub.authservice.common.exception.concrete.permission.PermissionNotFoundException;
import ecomhub.authservice.domain.entity.Permission;
import ecomhub.authservice.domain.repository.AccountRepositoryPort;
import ecomhub.authservice.domain.repository.PermissionRepositoryPort;
import ecomhub.authservice.domain.service.abstracts.PermissionService;
import ecomhub.authservice.domain.service.impl.PermissionServiceImpl;

import java.util.UUID;
import java.util.function.BiConsumer;

public abstract class AbstractPermissionUpdateHandler<TCommand> {
    protected final PermissionRepositoryPort permissionRepository;
    protected final AccountRepositoryPort accountRepository;
    protected final PermissionService permissionService;

    protected AbstractPermissionUpdateHandler(PermissionRepositoryPort permissionRepository, AccountRepositoryPort accountRepository) {
        this.permissionRepository = permissionRepository;
        this.accountRepository = accountRepository;
        this.permissionService = new PermissionServiceImpl();
    }

    protected void updateWithPermissionCheck(TCommand command, UUID permissionId, UUID requesterId, String forbiddenMessageError, BiConsumer<Permission, TCommand> updateAction) {
        var requester = accountRepository.geById(requesterId).orElseThrow(() -> new AccountNotFoundException(requesterId));
        var permission = permissionRepository.getById(permissionId).orElseThrow(() -> new PermissionNotFoundException(permissionId));
        if (!permissionService.canBeModifiedBy(requester)) throw new ForbiddenException(forbiddenMessageError);
        updateAction.accept(permission, command);
        int result = saveChange(permission, command);
        if (result == 0) throw new UpdateFailureException();
    }

    protected abstract int saveChange(Permission targetPermission, TCommand command);

}
