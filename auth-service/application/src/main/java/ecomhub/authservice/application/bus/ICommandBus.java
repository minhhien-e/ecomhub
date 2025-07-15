package ecomhub.authservice.application.bus;

import ecomhub.authservice.application.command.interfaces.ICommand;

public interface ICommandBus {
    void dispatch(ICommand command);
}
