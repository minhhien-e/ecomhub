package ecomhub.authservice.application.command.role.update.description;

import ecomhub.authservice.application.command.abstracts.ICommandHandler;
import ecomhub.authservice.application.command.role.update.abstracts.AbstractRoleUpdateHandler;
import ecomhub.authservice.application.port.repository.AccountRepositoryPort;
import ecomhub.authservice.application.port.repository.RoleRepositoryPort;
import ecomhub.authservice.domain.entity.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UpdateDescriptionRoleHandler extends AbstractRoleUpdateHandler<UpdateDescriptionRoleCommand>
        implements ICommandHandler<UpdateDescriptionRoleCommand> {

    public UpdateDescriptionRoleHandler(RoleRepositoryPort roleRepository, AccountRepositoryPort accountRepository) {
        super(roleRepository, accountRepository);
    }

    @Override
    protected int saveChange(Role targetRole, UpdateDescriptionRoleCommand command) {
        return roleRepository.updateDescription(targetRole.getId(), command.getNewDescription());
    }

    @Override
    public void handle(UpdateDescriptionRoleCommand command) {
        super.updateWithPermissionCheck(command,
                command.getRoleId(),
                command.getRequesterId(),
                "thay đổi miêu tả vai trò",
                (targetRole, updateCommand) ->
                        targetRole.updateDescription(updateCommand.getNewDescription()));
    }
}
