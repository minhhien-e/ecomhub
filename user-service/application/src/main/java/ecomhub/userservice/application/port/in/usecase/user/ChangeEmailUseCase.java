package ecomhub.userservice.application.port.in.usecase.user;

import ecomhub.userservice.application.dto.command.user.ChangeEmailCommand;
import ecomhub.userservice.application.port.in.usecase.base.UseCase;

public interface ChangeEmailUseCase extends UseCase<Void, ChangeEmailCommand> {
}
