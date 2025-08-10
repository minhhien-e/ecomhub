package ecomhub.authservice.application.command.role.delete;

import ecomhub.authservice.application.command.abstracts.ICommandHandler;
import ecomhub.authservice.application.port.repository.AccountRepositoryPort;
import ecomhub.authservice.application.port.repository.RoleRepositoryPort;
import ecomhub.authservice.common.exception.abstracts.ForbiddenException;
import ecomhub.authservice.common.exception.abstracts.UpdateFailureException;
import ecomhub.authservice.common.exception.concrete.account.AccountNotFoundException;
import ecomhub.authservice.common.exception.concrete.role.RoleNotFoundException;
import ecomhub.authservice.domain.service.abstracts.RoleService;
import ecomhub.authservice.domain.service.impl.RoleServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DeleteRoleHandler implements ICommandHandler<DeleteRoleCommand> {
    private final RoleRepositoryPort roleRepository;
    private final AccountRepositoryPort accountRepository;
    private final RoleService roleService;

    public DeleteRoleHandler(RoleRepositoryPort roleRepository, AccountRepositoryPort accountRepository) {
        this.roleRepository = roleRepository;
        this.accountRepository = accountRepository;
        this.roleService = new RoleServiceImpl();
    }

    @Override
    public void handle(DeleteRoleCommand command) {
        var targetRole = roleRepository.findById(command.getRoleId())
                .orElseThrow(() -> new RoleNotFoundException(command.getRoleId()));
        var requester = accountRepository
                .findById(command.getRequesterId()).orElseThrow(() -> new AccountNotFoundException(command.getRequesterId()));
        if (roleService.canBeModifiedBy(targetRole, requester)) {
            targetRole.deactivate();
            int result = roleRepository.updateActive(targetRole.getId(), false);
            if (result == 0)
                throw new UpdateFailureException();
        } else
            throw new ForbiddenException("xóa vai trò " + targetRole.getName());

    }
}
