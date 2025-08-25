package ecomhub.authservice.application.command.role.update.description;

import ecomhub.authservice.application.command.abstracts.ICommandHandler;
import ecomhub.authservice.application.command.role.update.abstracts.AbstractRoleUpdateHandler;
import ecomhub.authservice.domain.entity.Role;
import ecomhub.authservice.domain.repository.AccountRepositoryPort;
import ecomhub.authservice.domain.repository.RoleRepositoryPort;
import ecomhub.authservice.domain.service.abstracts.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UpdateDescriptionRoleHandler extends AbstractRoleUpdateHandler<String>
        implements ICommandHandler<UpdateDescriptionRoleCommand> {


    protected UpdateDescriptionRoleHandler(RoleRepositoryPort roleRepository, AccountRepositoryPort accountRepository, RoleService roleService) {
        super(roleRepository, accountRepository, roleService);
    }

    @Override
    public void handle(UpdateDescriptionRoleCommand command) {
        super.updateWithPermissionCheck(command.getNewDescription(),
                command.getRoleId(),
                command.getRequesterId(),
                roleService::updateDescription);
    }

    @Override
    protected int saveChange(Role targetRole) {
        return roleRepository.updateDescription(targetRole);
    }
}
