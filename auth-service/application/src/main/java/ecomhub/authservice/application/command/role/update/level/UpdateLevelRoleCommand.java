package ecomhub.authservice.application.command.role.update.level;

import ecomhub.authservice.application.command.abstracts.ICommand;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class UpdateLevelRoleCommand implements ICommand {
    private Integer newLevel;
    private UUID roleId;
    private UUID requesterId;
}
