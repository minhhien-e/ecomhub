package ecomhub.authservice.application.command.account.role.abstracts;

import ecomhub.authservice.common.exception.concrete.account.AccountNotFoundException;
import ecomhub.authservice.common.exception.concrete.role.RoleNotFoundException;
import ecomhub.authservice.domain.entity.Account;
import ecomhub.authservice.domain.entity.Role;
import ecomhub.authservice.domain.repository.AccountRepositoryPort;
import ecomhub.authservice.domain.repository.RoleRepositoryPort;
import ecomhub.authservice.domain.service.abstracts.AccountService;
import ecomhub.authservice.domain.service.impl.AccountServiceImpl;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.UUID;

public abstract class AbstractRoleManagementHandler<TCommand> {
    protected final AccountRepositoryPort accountRepository;
    protected final RoleRepositoryPort roleRepository;
    protected final AccountService accountService;

    public AbstractRoleManagementHandler(AccountRepositoryPort accountRepository, RoleRepositoryPort roleRepository) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.accountService = new AccountServiceImpl();
    }

    protected abstract void saveChange(TCommand command);

    protected void handle(TCommand command, UUID roleId, UUID accountId, UUID requesterId, TriConsumer<Account, Role, Account> action) {
        var role = roleRepository.findById(roleId).orElseThrow(() -> new RoleNotFoundException(roleId));
        var requester = accountRepository.findById(requesterId).orElseThrow(() -> new AccountNotFoundException(requesterId));
        var accountTarget = accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException(accountId));
        action.accept(accountTarget, role, requester);
        saveChange(command);
    }

}
