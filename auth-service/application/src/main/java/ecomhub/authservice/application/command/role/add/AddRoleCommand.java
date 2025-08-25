package ecomhub.authservice.application.command.role.add;

import ecomhub.authservice.application.command.abstracts.ICommand;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
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
