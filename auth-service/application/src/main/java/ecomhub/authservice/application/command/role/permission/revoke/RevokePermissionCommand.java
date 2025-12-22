package ecomhub.authservice.application.command.role.permission.revoke;

import ecomhub.authservice.application.command.abstracts.ICommand;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class RevokePermissionCommand implements ICommand {
    private UUID requesterId;
    private UUID roleId;
    private UUID permissionId;
}
