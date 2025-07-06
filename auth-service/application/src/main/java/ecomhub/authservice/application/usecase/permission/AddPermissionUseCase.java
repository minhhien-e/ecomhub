package ecomhub.authservice.application.usecase.permission;

import ecomhub.authservice.application.command.AddPermissionCommand;

public interface AddPermissionUseCase {
    void execute(AddPermissionCommand command);
}
