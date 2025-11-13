package ecomhub.userservice.application.port.in.usecase.user;

import ecomhub.userservice.application.dto.command.user.RenameUserCommand;
import ecomhub.userservice.application.port.in.usecase.base.UseCase;

public interface RenameUserUseCase extends UseCase<Void, RenameUserCommand> {
}
