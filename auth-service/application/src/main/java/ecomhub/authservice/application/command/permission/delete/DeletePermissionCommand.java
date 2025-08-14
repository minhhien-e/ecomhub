package ecomhub.authservice.application.command.permission.delete;

import ecomhub.authservice.application.command.abstracts.ICommand;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;
@Getter
@AllArgsConstructor
public class DeletePermissionCommand implements ICommand {
    private UUID permissionId;
    private UUID requesterId;
}
