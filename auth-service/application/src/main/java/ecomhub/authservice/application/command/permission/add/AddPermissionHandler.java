package ecomhub.authservice.application.command.permission.add;

import ecomhub.authservice.application.command.abstracts.ICommandHandler;
import ecomhub.authservice.application.mapper.PermissionMapper;
import ecomhub.authservice.common.exception.concrete.permission.PermissionAlreadyExistsException;
import ecomhub.authservice.domain.repository.PermissionRepositoryPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddPermissionHandler implements ICommandHandler<AddPermissionCommand> {
    private final PermissionRepositoryPort permissionRepository;

    @Transactional
    @Override
    public void handle(AddPermissionCommand command) {
        if (permissionRepository.existsByName(command.getName())) {
            throw new PermissionAlreadyExistsException(command.getName());
        }
        permissionRepository.save(PermissionMapper.toDomain(command));
    }
}
