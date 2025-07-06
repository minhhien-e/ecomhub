package ecomhub.authservice.adapter.facade;

import ecomhub.authservice.application.command.AddPermissionCommand;
import ecomhub.authservice.application.usecase.permission.AddPermissionUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionFacade {
    private final AddPermissionUseCase addPermissionUseCase;

    public void addPermission(AddPermissionCommand command) {
        addPermissionUseCase.execute(command);
    }


}
