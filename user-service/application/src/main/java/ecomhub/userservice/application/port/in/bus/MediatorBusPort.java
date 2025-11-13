package ecomhub.userservice.application.port.in.bus;

import ecomhub.userservice.application.dto.command.base.Command;
import ecomhub.userservice.application.dto.query.base.Query;

public interface MediatorBusPort {
    <R, C extends Command<R>> R sendCommand(C command);

    <R, Q extends Query<R>> R sendQuery(Q query);
}
