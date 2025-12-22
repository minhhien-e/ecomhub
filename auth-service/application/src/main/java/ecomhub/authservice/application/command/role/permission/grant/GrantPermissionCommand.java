package ecomhub.authservice.application.command.role.permission.grant;

import ecomhub.authservice.application.command.abstracts.ICommand;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class GrantPermissionCommand implements ICommand {
    private UUID requesterId;
    private UUID roleId;
    private Set<String> permissionKeys;
}
