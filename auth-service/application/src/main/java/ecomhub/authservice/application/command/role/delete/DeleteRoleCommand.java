package ecomhub.authservice.application.command.role.delete;

import ecomhub.authservice.application.command.abstracts.ICommand;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class DeleteRoleCommand implements ICommand {
    private UUID roleId;
    private UUID requesterId;

    public DeleteRoleCommand(UUID roleId) {
        this.roleId = roleId;
    }
}
