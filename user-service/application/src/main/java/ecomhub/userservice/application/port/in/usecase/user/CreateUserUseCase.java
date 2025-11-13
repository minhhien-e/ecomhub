package ecomhub.userservice.application.port.in.usecase.user;

import ecomhub.userservice.application.dto.command.user.CreateUserCommand;
import ecomhub.userservice.application.port.in.usecase.base.UseCase;

public interface CreateUserUseCase extends UseCase<Void, CreateUserCommand> {
}
