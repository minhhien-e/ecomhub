package ecomhub.authservice.application.command.role.update.abstracts;

import ecomhub.authservice.common.exception.abstracts.UpdateFailureException;
import ecomhub.authservice.domain.entity.Account;
import ecomhub.authservice.domain.entity.Role;
import ecomhub.authservice.domain.repository.AccountRepositoryPort;
import ecomhub.authservice.domain.repository.RoleRepositoryPort;
import ecomhub.authservice.domain.service.abstracts.RoleService;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.UUID;

public abstract class AbstractRoleUpdateHandler<TValue> {
    protected final RoleRepositoryPort roleRepository;
    protected final AccountRepositoryPort accountRepository;
    protected final RoleService roleService;

    protected AbstractRoleUpdateHandler(RoleRepositoryPort roleRepository,
                                        AccountRepositoryPort accountRepository, RoleService roleService) {
        this.roleRepository = roleRepository;
        this.accountRepository = accountRepository;
        this.roleService = roleService;
    }

    protected void updateWithPermissionCheck(
            TValue newValue,
            UUID roleId,
            UUID requesterId,
            TriConsumer<Role, Account, TValue> updateAction
    ) {
        var targetRole = roleRepository.getById(roleId);
        var requester = accountRepository.geById(requesterId);
        updateAction.accept(targetRole, requester, newValue);
        int result = saveChange(targetRole);
        if (result == 0) throw new UpdateFailureException();
    }

    protected abstract int saveChange(Role targetRole);

}
