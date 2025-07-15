package ecomhub.authservice.application.command.interfaces;

public interface ICommandHandler<C extends ICommand> {
    void handle(C command);
}
