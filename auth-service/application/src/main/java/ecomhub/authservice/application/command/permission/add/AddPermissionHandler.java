package ecomhub.authservice.application.command.permission.add;

import ecomhub.authservice.application.command.abstracts.ICommandHandler;
import ecomhub.authservice.application.mapper.PermissionCommandMapper;
import ecomhub.authservice.application.port.repository.PermissionRepositoryPort;
import ecomhub.authservice.common.exception.concrete.permission.PermissionAlreadyExistsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddPermissionHandler implements ICommandHandler<AddPermissionCommand> {
    private final PermissionRepositoryPort permissionRepository;
    private final PermissionCommandMapper permissionCommandMapper;

    @Transactional
    @Override
    public void handle(AddPermissionCommand command) {
        if (permissionRepository.existsByName(command.getName())) {
            throw new PermissionAlreadyExistsException(command.getName());
        }
        permissionRepository.save(permissionCommandMapper.toDomain(command));
    }
}
