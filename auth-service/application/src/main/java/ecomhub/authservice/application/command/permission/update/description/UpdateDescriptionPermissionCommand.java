package ecomhub.authservice.application.command.permission.update.description;

import ecomhub.authservice.application.command.abstracts.ICommand;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class UpdateDescriptionPermissionCommand implements ICommand {
    private String newDescription;
    private UUID permissionId;
}
