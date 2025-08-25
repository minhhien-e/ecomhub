package ecomhub.authservice.application.command.permission.delete;

import ecomhub.authservice.application.command.abstracts.ICommandHandler;
import ecomhub.authservice.common.exception.abstracts.ForbiddenException;
import ecomhub.authservice.common.exception.concrete.account.AccountNotFoundException;
import ecomhub.authservice.common.exception.concrete.permission.PermissionNotFoundException;
import ecomhub.authservice.domain.repository.AccountRepositoryPort;
import ecomhub.authservice.domain.repository.PermissionRepositoryPort;
import ecomhub.authservice.domain.service.abstracts.PermissionService;
import ecomhub.authservice.domain.service.impl.PermissionServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DeletePermissionHandler implements ICommandHandler<DeletePermissionCommand> {
    private final PermissionRepositoryPort permissionRepository;
    private final AccountRepositoryPort accountRepository;
    private final PermissionService permissionService;

    public DeletePermissionHandler(PermissionRepositoryPort permissionRepository, AccountRepositoryPort accountRepository) {
        this.permissionRepository = permissionRepository;
        this.accountRepository = accountRepository;
        this.permissionService = new PermissionServiceImpl();
    }

    @Override
    public void handle(DeletePermissionCommand command) {
        var requester = accountRepository.geById(command.getRequesterId()).orElseThrow(() -> new AccountNotFoundException(command.getRequesterId()));
        if (!permissionRepository.existsById(command.getPermissionId()))
            throw new PermissionNotFoundException(command.getPermissionId());
        if (!permissionService.canBeDeletedBy(requester))
            throw new ForbiddenException("xóa quyền hạn");
        permissionRepository.deleteById(command.getPermissionId());
    }
}
