package ecomhub.authservice.application.command.account.role.revoke;

import ecomhub.authservice.application.command.abstracts.ICommand;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class RevokeRoleCommand implements ICommand {
    private UUID requesterId;
    private UUID roleId;
    private UUID accountId;
}
