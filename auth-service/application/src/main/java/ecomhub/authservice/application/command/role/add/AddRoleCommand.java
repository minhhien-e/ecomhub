package ecomhub.authservice.application.command.role.add;

import ecomhub.authservice.application.command.abstracts.ICommand;
import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class AddRoleCommand implements ICommand {
    private UUID requesterId;
    private String name;
    private String key;
    private String type;
    private String description;
    private int level;
    private Set<String> permissionKeys;
}
