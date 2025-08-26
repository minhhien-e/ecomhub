package ecomhub.authservice.application.command.permission.update.name;

import ecomhub.authservice.application.command.abstracts.ICommandHandler;
import ecomhub.authservice.application.command.permission.update.abstracts.AbstractPermissionUpdateHandler;
import ecomhub.authservice.domain.entity.Permission;
import ecomhub.authservice.domain.repository.PermissionRepositoryPort;
import ecomhub.authservice.domain.service.abstracts.PermissionService;
import org.springframework.stereotype.Service;

@Service
public class UpdateNamePermissionHandler extends AbstractPermissionUpdateHandler<String> implements ICommandHandler<UpdateNamePermissionCommand> {

    protected UpdateNamePermissionHandler(PermissionRepositoryPort permissionRepository, PermissionService permissionService) {
        super(permissionRepository, permissionService);
    }

    @Override
    public void handle(UpdateNamePermissionCommand command) {
        updateWithPermissionCheck(
                command.getNewName(),
                command.getPermissionId(),
                permissionService::updateName
        );
    }

    @Override
    protected int saveChange(Permission targetPermission) {
        return permissionRepository.updateName(targetPermission);
    }
}
