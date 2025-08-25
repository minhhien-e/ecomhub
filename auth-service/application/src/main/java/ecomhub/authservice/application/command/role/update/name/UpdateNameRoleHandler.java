package ecomhub.authservice.application.command.role.update.name;

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
public class UpdateNameRoleHandler extends AbstractRoleUpdateHandler<String>
        implements ICommandHandler<UpdateNameRoleCommand> {
    protected UpdateNameRoleHandler(RoleRepositoryPort roleRepository, AccountRepositoryPort accountRepository, RoleService roleService) {
        super(roleRepository, accountRepository, roleService);
    }

    @Override
    public void handle(UpdateNameRoleCommand command) {
        super.updateWithPermissionCheck(command.getNewName(),
                command.getRoleId(),
                command.getRequesterId(),
                roleService::updateName);
    }

    @Override
    protected int saveChange(Role targetRole) {
        return roleRepository.updateName(targetRole);
    }
}
