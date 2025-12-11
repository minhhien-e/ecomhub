package ecomhub.userservice.application.port.in.usecase.user;

import ecomhub.userservice.application.dto.command.user.DeleteUserCommand;
import ecomhub.userservice.application.port.in.usecase.base.UseCase;

public interface DeleteUserUseCase extends UseCase<Void, DeleteUserCommand> {
}
