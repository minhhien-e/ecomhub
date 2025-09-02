package ecomhub.authservice.application.command.role.delete;

import ecomhub.authservice.application.command.abstracts.ICommandHandler;
import ecomhub.authservice.domain.repository.AccountRepositoryPort;
import ecomhub.authservice.domain.repository.RoleRepositoryPort;
import ecomhub.authservice.domain.service.abstracts.RoleService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class HardDeleteRoleHandler implements ICommandHandler<DeleteRoleCommand> {
    private final RoleRepositoryPort roleRepository;
    private final AccountRepositoryPort accountRepository;
    private final RoleService roleService;

    public HardDeleteRoleHandler(RoleRepositoryPort roleRepository, AccountRepositoryPort accountRepository, RoleService roleService) {
        this.roleRepository = roleRepository;
        this.accountRepository = accountRepository;
        this.roleService = roleService;
    }

    @Override
    public void handle(DeleteRoleCommand command) {
        var targetRole = roleRepository.getById(command.getRoleId());
        var requester = accountRepository.geById(command.getRequesterId());
        roleService.delete(targetRole, requester, true);
        roleRepository.deleteById(targetRole.getId());
    }
}
