package ecomhub.userservice.application.port.in.usecase.user;

import ecomhub.userservice.application.dto.command.user.ChangeGenderCommand;
import ecomhub.userservice.application.port.in.usecase.base.UseCase;

public interface ChangeGenderUseCase extends UseCase<Void, ChangeGenderCommand> {
}
