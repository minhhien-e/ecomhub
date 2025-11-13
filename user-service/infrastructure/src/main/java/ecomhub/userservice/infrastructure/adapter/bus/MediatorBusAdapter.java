package ecomhub.userservice.infrastructure.adapter.bus;

import ecomhub.userservice.application.dto.command.base.Command;
import ecomhub.userservice.application.dto.query.base.Query;
import ecomhub.userservice.application.port.in.bus.MediatorBusPort;
import io.github.admiralxy.mediatr.api.Mediator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MediatorBusAdapter implements MediatorBusPort {
    private final Mediator mediator;

    @Override
    public <R, C extends Command<R>> R sendCommand(C command) {
        return mediator.send(new MediatorCommand<>(command));
    }

    @Override
    public <R, Q extends Query<R>> R sendQuery(Q query) {
        return mediator.send(new MediatorQuery<>(query));
    }
}
