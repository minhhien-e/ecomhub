package ecomhub.authservice.application.command.role.update.name;

import ecomhub.authservice.application.command.abstracts.ICommand;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class UpdateNameRoleCommand implements ICommand {
    private String newName;
    private UUID roleId;
    private UUID requesterId;
}
