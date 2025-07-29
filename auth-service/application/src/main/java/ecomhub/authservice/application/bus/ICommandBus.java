package ecomhub.authservice.application.bus;

import ecomhub.authservice.application.command.abstracts.ICommand;

public interface ICommandBus {
    void dispatch(ICommand command);
}
