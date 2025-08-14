package ecomhub.authservice.application.command.permission.update.description;

import ecomhub.authservice.application.command.abstracts.ICommandHandler;
import ecomhub.authservice.application.command.permission.update.abstracts.AbstractPermissionUpdateHandler;
import ecomhub.authservice.domain.entity.Permission;
import ecomhub.authservice.domain.repository.AccountRepositoryPort;
import ecomhub.authservice.domain.repository.PermissionRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class UpdateDescriptionPermissionHandler extends AbstractPermissionUpdateHandler<UpdateDescriptionPermissionCommand>
        implements ICommandHandler<UpdateDescriptionPermissionCommand> {
    protected UpdateDescriptionPermissionHandler(PermissionRepositoryPort permissionRepository, AccountRepositoryPort accountRepository) {
        super(permissionRepository, accountRepository);
    }

    @Override
    protected int saveChange(Permission targetPermission, UpdateDescriptionPermissionCommand updateDescriptionPermissionCommand) {
        return permissionRepository.updateDescription(targetPermission.getId(), updateDescriptionPermissionCommand.getNewDescription());
    }

    @Override
    public void handle(UpdateDescriptionPermissionCommand command) {
        updateWithPermissionCheck(
                command,
                command.getPermissionId(),
                command.getRequesterId(),
                "thay đổi thông tin miêu tả của quyền",
                (targetPermission, updateCommand) -> targetPermission
                        .updateDescription(updateCommand.getNewDescription())
        );
    }

}
