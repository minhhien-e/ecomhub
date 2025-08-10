package ecomhub.authservice.application.command.role.update.level;

import ecomhub.authservice.application.command.abstracts.ICommandHandler;
import ecomhub.authservice.application.command.role.update.abstracts.AbstractRoleUpdateHandler;
import ecomhub.authservice.application.port.repository.AccountRepositoryPort;
import ecomhub.authservice.application.port.repository.RoleRepositoryPort;
import ecomhub.authservice.domain.entity.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UpdateLevelRoleHandler extends AbstractRoleUpdateHandler<UpdateLevelRoleCommand>
        implements ICommandHandler<UpdateLevelRoleCommand> {

    public UpdateLevelRoleHandler(RoleRepositoryPort roleRepository, AccountRepositoryPort accountRepository) {
        super(roleRepository, accountRepository);
    }

    @Override
    public void handle(UpdateLevelRoleCommand command) {
        super.updateWithPermissionCheck(command,
                command.getRoleId(),
                command.getRequesterId(),
                "thay đổi cấp độ vai trò",
                (targetRole, updateCommand) ->
                        targetRole.updateLevel(updateCommand.getNewLevel()));
    }

    @Override
    protected int saveChange(Role targetRole, UpdateLevelRoleCommand command) {
        return roleRepository.updateLevel(targetRole.getId(), command.getNewLevel());
    }
}
