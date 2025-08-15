package ecomhub.authservice.application.command.account.role.grant;

import ecomhub.authservice.application.command.abstracts.ICommandHandler;
import ecomhub.authservice.application.command.account.role.abstracts.AbstractRoleManagementHandler;
import ecomhub.authservice.common.exception.abstracts.ForbiddenException;
import ecomhub.authservice.domain.repository.AccountRepositoryPort;
import ecomhub.authservice.domain.repository.RoleRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class GrantRoleHandler extends AbstractRoleManagementHandler<GrantRoleCommand> implements ICommandHandler<GrantRoleCommand> {
    public GrantRoleHandler(AccountRepositoryPort accountRepository, RoleRepositoryPort roleRepository) {
        super(accountRepository, roleRepository);
    }

    @Override
    public void handle(GrantRoleCommand command) {
        super.handle(command, command.getRoleId(), command.getRequesterId(), command.getAccountId(),
                (accountTarget, role, requester) -> {
                    if (accountService.canBeGrantedRoleBy(accountTarget, role, requester))
                        accountTarget.grantRole(role);
                    else throw new ForbiddenException("gán vai trò cho tài khoản");
                });

    }

    @Override
    protected void saveChange(GrantRoleCommand revokeRoleCommand) {
        accountRepository.grantRole(revokeRoleCommand.getAccountId(), revokeRoleCommand.getRoleId());
    }
}
