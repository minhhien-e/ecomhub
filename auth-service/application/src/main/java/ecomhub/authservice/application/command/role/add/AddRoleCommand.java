package ecomhub.authservice.application.command.role.add;

import ecomhub.authservice.application.command.interfaces.ICommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddRoleCommand implements ICommand {
    @NotBlank
    private String name;
    private String description;
    @NotEmpty(message = "Quyền không được bỏ trống")
    private Set<@NotNull(message = "ID quyền không không được bỏ trống") UUID> permissionIds;
}
