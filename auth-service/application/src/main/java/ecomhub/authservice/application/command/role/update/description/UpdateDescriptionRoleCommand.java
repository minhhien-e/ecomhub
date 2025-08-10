package ecomhub.authservice.application.command.role.update.description;

import ecomhub.authservice.application.command.abstracts.ICommand;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
public class UpdateDescriptionRoleCommand implements ICommand {
    private String newDescription;
    private UUID roleId;
    private UUID requesterId;
}
