package ecomhub.authservice.application.command.role.update.level;

import ecomhub.authservice.application.command.abstracts.ICommand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class UpdateLevelRoleCommand implements ICommand {
    private Integer newLevel;
    private UUID roleId;
    private UUID requesterId;
}
