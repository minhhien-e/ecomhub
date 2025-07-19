package ecomhub.authservice.application.command.permission.add;

import ecomhub.authservice.application.command.interfaces.ICommand;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddPermissionCommand implements ICommand {
    public @NotBlank(message = "Tên quyền không được bỏ trống") String name;
    public String description;
}
