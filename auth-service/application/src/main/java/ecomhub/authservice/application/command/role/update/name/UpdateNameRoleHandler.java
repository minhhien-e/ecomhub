package ecomhub.authservice.application.command.role.update.name;

import ecomhub.authservice.application.command.abstracts.ICommandHandler;
import ecomhub.authservice.application.command.role.update.abstracts.AbstractRoleUpdateHandler;
import ecomhub.authservice.domain.repository.AccountRepositoryPort;
import ecomhub.authservice.domain.repository.RoleRepositoryPort;
import ecomhub.authservice.domain.entity.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UpdateNameRoleHandler extends AbstractRoleUpdateHandler<UpdateNameRoleCommand>
        implements ICommandHandler<UpdateNameRoleCommand> {
    public UpdateNameRoleHandler(RoleRepositoryPort roleRepository, AccountRepositoryPort accountRepository) {
        super(roleRepository, accountRepository);
    }

    @Override
    public void handle(UpdateNameRoleCommand command) {
        super.updateWithPermissionCheck(command,
                command.getRoleId(),
                command.getRequesterId(),
                "thay đổi tên vai trò",
                (targetRole, updateCommand) ->
                        targetRole.updateName(updateCommand.getNewName()));
    }

    @Override
    protected int saveChange(Role targetRole, UpdateNameRoleCommand command) {
        return roleRepository.updateName(targetRole.getId(), command.getNewName());
    }
}
