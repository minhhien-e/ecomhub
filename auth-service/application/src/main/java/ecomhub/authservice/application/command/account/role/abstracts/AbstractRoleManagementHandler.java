package ecomhub.authservice.application.command.account.role.abstracts;

import ecomhub.authservice.common.utils.TriConsumer;
import ecomhub.authservice.domain.entity.Account;
import ecomhub.authservice.domain.entity.Role;
import ecomhub.authservice.domain.repository.AccountRepositoryPort;
import ecomhub.authservice.domain.repository.RoleRepositoryPort;
import ecomhub.authservice.domain.service.abstracts.AccountService;

import java.util.UUID;

public abstract class AbstractRoleManagementHandler<TCommand> {
    protected final AccountRepositoryPort accountRepository;
    protected final RoleRepositoryPort roleRepository;
    protected final AccountService accountService;

    public AbstractRoleManagementHandler(AccountRepositoryPort accountRepository, RoleRepositoryPort roleRepository, AccountService accountService) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.accountService = accountService;
    }

    protected abstract void saveChange(TCommand command);

    protected void handle(TCommand command, UUID roleId, UUID accountId, UUID requesterId, TriConsumer<Account, Role, Account> action) {
        var role = roleRepository.getById(roleId);
        var requester = accountRepository.geById(requesterId);
        var accountTarget = accountRepository.geById(accountId);
        action.accept(accountTarget, role, requester);
        saveChange(command);
    }

}
