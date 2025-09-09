package ecomhub.authservice.application.command.account.register;

import ecomhub.authservice.application.command.abstracts.ICommand;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterAccountCommand implements ICommand {
    private String username;
    private String password;
    private String passwordConfirm;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String provider;
}

