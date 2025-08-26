package ecomhub.authservice.application.command.permission.update.description;

import ecomhub.authservice.application.command.abstracts.ICommandHandler;
import ecomhub.authservice.application.command.permission.update.abstracts.AbstractPermissionUpdateHandler;
import ecomhub.authservice.domain.entity.Permission;
import ecomhub.authservice.domain.repository.PermissionRepositoryPort;
import ecomhub.authservice.domain.service.abstracts.PermissionService;
import org.springframework.stereotype.Service;

@Service
public class UpdateDescriptionPermissionHandler extends AbstractPermissionUpdateHandler<String>
        implements ICommandHandler<UpdateDescriptionPermissionCommand> {

    protected UpdateDescriptionPermissionHandler(PermissionRepositoryPort permissionRepository, PermissionService permissionService) {
        super(permissionRepository, permissionService);
    }

    @Override
    protected int saveChange(Permission targetPermission) {
        return permissionRepository.updateDescription(targetPermission);
    }

    @Override
    public void handle(UpdateDescriptionPermissionCommand command) {
        updateWithPermissionCheck(
                command.getNewDescription(),
                command.getPermissionId(),
                permissionService::updateDescription
        );
    }

}
