package ecomhub.authservice.adapter.facade;

import ecomhub.authservice.application.command.AddRoleCommand;
import ecomhub.authservice.application.usecase.role.AddRoleUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleFacade {
    private final AddRoleUseCase addRoleUseCase;

    public void addRole(AddRoleCommand command) {
        addRoleUseCase.execute(command);
    }
}
