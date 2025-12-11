package ecomhub.authservice.application.command.abstracts;

public interface ICommandHandler<C extends ICommand> {
    void handle(C command);
}
