package ecomhub.authservice.application.command.permission.update.name;

import ecomhub.authservice.application.command.abstracts.ICommand;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class UpdateNamePermissionCommand implements ICommand {
    private String newName;
    private UUID permissionId;
}
