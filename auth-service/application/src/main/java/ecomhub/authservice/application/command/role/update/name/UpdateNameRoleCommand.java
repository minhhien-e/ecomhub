package ecomhub.authservice.application.command.role.update.name;

import ecomhub.authservice.application.command.abstracts.ICommand;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class UpdateNameRoleCommand implements ICommand {
    private String newName;
    private UUID roleId;
    private UUID requesterId;
}
