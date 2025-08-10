package ecomhub.authservice.application.command.role.delete;

import ecomhub.authservice.application.command.abstracts.ICommand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class DeleteRoleCommand implements ICommand {
    private UUID roleId;
    private UUID requesterId;
}
