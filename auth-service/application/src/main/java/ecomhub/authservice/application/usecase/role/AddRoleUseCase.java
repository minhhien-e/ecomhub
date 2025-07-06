package ecomhub.authservice.application.usecase.role;

import ecomhub.authservice.application.command.AddRoleCommand;

public interface AddRoleUseCase {
    void execute(AddRoleCommand command);
}
