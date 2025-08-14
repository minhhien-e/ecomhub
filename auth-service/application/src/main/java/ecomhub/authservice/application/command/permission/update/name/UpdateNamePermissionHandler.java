package ecomhub.authservice.application.command.permission.update.name;

import ecomhub.authservice.application.command.abstracts.ICommandHandler;
import ecomhub.authservice.application.command.permission.update.abstracts.AbstractPermissionUpdateHandler;
import ecomhub.authservice.domain.entity.Permission;
import ecomhub.authservice.domain.repository.AccountRepositoryPort;
import ecomhub.authservice.domain.repository.PermissionRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class UpdateNamePermissionHandler extends AbstractPermissionUpdateHandler<UpdateNamePermissionCommand> implements ICommandHandler<UpdateNamePermissionCommand> {
    protected UpdateNamePermissionHandler(PermissionRepositoryPort permissionRepository, AccountRepositoryPort accountRepository) {
        super(permissionRepository, accountRepository);
    }

    @Override
    public void handle(UpdateNamePermissionCommand command) {
        updateWithPermissionCheck(
                command,
                command.getPermissionId(),
                command.getRequesterId(),
                "thay đổi tên của quyền",
                (targetPermission, updateCommand) -> targetPermission
                        .updateName(updateCommand.getNewName())
        );
    }

    @Override
    protected int saveChange(Permission targetPermission, UpdateNamePermissionCommand updateNamePermissionCommand) {
        return permissionRepository.updateName(targetPermission.getId(), updateNamePermissionCommand.getNewName());
    }
}
