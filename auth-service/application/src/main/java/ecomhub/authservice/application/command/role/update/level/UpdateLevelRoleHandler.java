package ecomhub.authservice.application.command.role.update.level;

import ecomhub.authservice.application.command.abstracts.ICommandHandler;
import ecomhub.authservice.application.command.role.update.abstracts.AbstractRoleUpdateHandler;
import ecomhub.authservice.domain.repository.AccountRepositoryPort;
import ecomhub.authservice.domain.repository.RoleRepositoryPort;
import ecomhub.authservice.domain.entity.Role;
import ecomhub.authservice.domain.service.abstracts.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UpdateLevelRoleHandler extends AbstractRoleUpdateHandler<Integer>
        implements ICommandHandler<UpdateLevelRoleCommand> {
    protected UpdateLevelRoleHandler(RoleRepositoryPort roleRepository, AccountRepositoryPort accountRepository, RoleService roleService) {
        super(roleRepository, accountRepository, roleService);
    }

    @Override
    public void handle(UpdateLevelRoleCommand command) {
        super.updateWithPermissionCheck(command.getNewLevel(),
                command.getRoleId(),
                command.getRequesterId(),
                roleService::updateLevel);
    }

    @Override
    protected int saveChange(Role targetRole) {
        return roleRepository.updateLevel(targetRole);
    }
}
