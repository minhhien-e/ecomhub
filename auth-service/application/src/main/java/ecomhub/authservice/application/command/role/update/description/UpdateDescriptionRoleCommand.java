package ecomhub.authservice.application.command.role.update.description;

import ecomhub.authservice.application.command.abstracts.ICommand;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;
@Getter
@AllArgsConstructor
public class UpdateDescriptionRoleCommand implements ICommand {
    private String newDescription;
    private UUID roleId;
    private UUID requesterId;

}
