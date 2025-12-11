package ecomhub.authservice.application.port.bus;

import ecomhub.authservice.application.command.abstracts.ICommand;

public interface ICommandBus {
    void dispatch(ICommand command);
}
