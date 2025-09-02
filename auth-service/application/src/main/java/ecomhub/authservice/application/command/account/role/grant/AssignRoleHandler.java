package ecomhub.authservice.application.command.account.role.grant;

import ecomhub.authservice.application.command.abstracts.ICommandHandler;
import ecomhub.authservice.application.command.account.role.abstracts.AbstractRoleManagementHandler;
import ecomhub.authservice.domain.repository.AccountRepositoryPort;
import ecomhub.authservice.domain.repository.RoleRepositoryPort;
import ecomhub.authservice.domain.service.abstracts.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AssignRoleHandler extends AbstractRoleManagementHandler<AssignRoleCommand> implements ICommandHandler<AssignRoleCommand> {
    public AssignRoleHandler(AccountRepositoryPort accountRepository, RoleRepositoryPort roleRepository, AccountService accountService) {
        super(accountRepository, roleRepository, accountService);
    }

    @Override
    public void handle(AssignRoleCommand command) {
        super.handle(command, command.getRoleId(), command.getRequesterId(), command.getAccountId(),
                accountService::assignRole);
    }

    @Override
    protected void saveChange(AssignRoleCommand command) {
        accountRepository.assignRole(command.getAccountId(), command.getRoleId());
    }
}
