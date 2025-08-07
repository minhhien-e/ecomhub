package ecomhub.authservice.application.command.role.delete;

import ecomhub.authservice.application.command.abstracts.ICommandHandler;
import ecomhub.authservice.application.port.repository.RoleRepositoryPort;
import ecomhub.authservice.common.exception.abstracts.ForbiddenException;
import ecomhub.authservice.common.exception.abstracts.UpdateFailureException;
import ecomhub.authservice.common.exception.concrete.role.RoleNotFoundException;
import ecomhub.authservice.domain.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteRoleHandler implements ICommandHandler<DeleteRoleCommand> {
    private final RoleRepositoryPort roleRepository;

    @Override
    public void handle(DeleteRoleCommand command) {
        var roleTarget = roleRepository.findById(command.getRoleId())
                .orElseThrow(() -> new RoleNotFoundException(command.getRoleId()));
        var requesterRoles = roleRepository
                .findByAccountIdAndHigherLevelThan(command.getRequesterId(), roleTarget.getLevel().value());
        for (Role role : requesterRoles) {
            roleTarget.deactivateBy(false, role);
            if (!role.isActive()) {
                break;
            }
        }
        if (roleTarget.isActive())
            throw new ForbiddenException("xóa vai trò " + roleTarget.getName());
        int colCount = roleRepository.updateActive(roleTarget.getId(), false);
        if (colCount == 0)
            throw new UpdateFailureException();
    }
}
