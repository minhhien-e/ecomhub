package ecomhub.authservice.application.command.role.update.abstracts;

import ecomhub.authservice.domain.repository.AccountRepositoryPort;
import ecomhub.authservice.domain.repository.RoleRepositoryPort;
import ecomhub.authservice.common.exception.abstracts.ForbiddenException;
import ecomhub.authservice.common.exception.abstracts.UpdateFailureException;
import ecomhub.authservice.common.exception.concrete.account.AccountNotFoundException;
import ecomhub.authservice.common.exception.concrete.role.RoleNotFoundException;
import ecomhub.authservice.domain.entity.Role;
import ecomhub.authservice.domain.service.abstracts.RoleService;
import ecomhub.authservice.domain.service.impl.RoleServiceImpl;

import java.util.UUID;
import java.util.function.BiConsumer;

public abstract class AbstractRoleUpdateHandler<TCommand> {
    protected final RoleRepositoryPort roleRepository;
    protected final AccountRepositoryPort accountRepository;
    protected final RoleService roleService;

    protected AbstractRoleUpdateHandler(RoleRepositoryPort roleRepository,
                                        AccountRepositoryPort accountRepository) {
        this.roleRepository = roleRepository;
        this.accountRepository = accountRepository;
        roleService = new RoleServiceImpl();
    }
    protected void updateWithPermissionCheck(
            TCommand command,
            UUID roleId,
            UUID requesterId,
            String forbiddenMessageError,
            BiConsumer<Role, TCommand> updateAction
    ) {
        var targetRole = roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException(roleId));
        var requester = accountRepository.findById(requesterId)
                .orElseThrow(() -> new AccountNotFoundException(requesterId));

        if (!roleService.canBeModifiedBy(targetRole, requester)) {
            throw new ForbiddenException(forbiddenMessageError);
        }

        updateAction.accept(targetRole, command);
        int result = saveChange(targetRole, command);
        if (result == 0) throw new UpdateFailureException();
    }
    protected abstract int saveChange(Role targetRole, TCommand command);

}
