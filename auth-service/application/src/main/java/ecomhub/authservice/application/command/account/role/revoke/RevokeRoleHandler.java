package ecomhub.authservice.application.command.account.role.revoke;

import ecomhub.authservice.application.command.abstracts.ICommandHandler;
import ecomhub.authservice.application.command.account.role.abstracts.AbstractRoleManagementHandler;
import ecomhub.authservice.common.exception.abstracts.ForbiddenException;
import ecomhub.authservice.domain.repository.AccountRepositoryPort;
import ecomhub.authservice.domain.repository.RoleRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class RevokeRoleHandler extends AbstractRoleManagementHandler<RevokeRoleCommand> implements ICommandHandler<RevokeRoleCommand> {

    public RevokeRoleHandler(AccountRepositoryPort accountRepository, RoleRepositoryPort roleRepository) {
        super(accountRepository, roleRepository);
    }

    @Override
    public void handle(RevokeRoleCommand command) {
        super.handle(command, command.getRoleId(), command.getRequesterId(), command.getAccountId(),
                (accountTarget, role, requester) -> {
                    if (accountService.canBeRevokedRoleBy(accountTarget, role, requester))
                        accountTarget.revokeRole(role);
                    else throw new ForbiddenException("gán vai trò cho tài khoản");
                });
    }

    @Override
    protected void saveChange(RevokeRoleCommand revokeRoleCommand) {
        accountRepository.revokeRole(revokeRoleCommand.getAccountId(), revokeRoleCommand.getRoleId());
    }
}
