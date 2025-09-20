package ecomhub.authservice.application.command.account.role.revoke;

import ecomhub.authservice.application.command.abstracts.ICommandHandler;
import ecomhub.authservice.application.command.account.role.abstracts.AbstractRoleManagementHandler;
import ecomhub.authservice.domain.repository.AccountRepositoryPort;
import ecomhub.authservice.domain.repository.RoleRepositoryPort;
import ecomhub.authservice.domain.service.abstracts.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RevokeRoleHandler extends AbstractRoleManagementHandler<RevokeRoleCommand> implements ICommandHandler<RevokeRoleCommand> {

    public RevokeRoleHandler(AccountRepositoryPort accountRepository, RoleRepositoryPort roleRepository, AccountService accountService) {
        super(accountRepository, roleRepository, accountService);
    }

    @Override
    public void handle(RevokeRoleCommand command) {
        super.handle(command, command.getRoleId(), command.getRequesterId(), command.getAccountId(),
                accountService::revokeRole);
    }

    @Override
    protected void saveChange(RevokeRoleCommand revokeRoleCommand) {
        accountRepository.revokeRole(revokeRoleCommand.getAccountId(), revokeRoleCommand.getRoleId());
    }
}
