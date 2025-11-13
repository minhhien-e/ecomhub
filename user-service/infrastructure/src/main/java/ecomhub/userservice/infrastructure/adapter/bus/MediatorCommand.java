package ecomhub.userservice.infrastructure.adapter.bus;

import ecomhub.userservice.application.dto.command.base.Command;
import io.github.admiralxy.mediatr.api.Request;

public record MediatorCommand<R, C extends Command<R>>(C command) implements Request<R> {
}
