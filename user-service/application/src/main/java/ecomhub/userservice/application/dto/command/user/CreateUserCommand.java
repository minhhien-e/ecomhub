package ecomhub.userservice.application.dto.command.user;

import ecomhub.userservice.application.dto.command.base.Command;

public record CreateUserCommand(String fullName, String email, String phoneNumber) implements Command<Void> {
}
