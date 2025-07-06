package ecomhub.authservice.application.usecase.account;

import ecomhub.authservice.application.command.RegisterAccountCommand;

public interface RegisterAccountUseCase {
    void execute(RegisterAccountCommand command);
}
