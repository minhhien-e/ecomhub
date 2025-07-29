package ecomhub.authservice.application.command.permission.add;

import ecomhub.authservice.application.command.abstracts.ICommand;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddPermissionCommand implements ICommand {
    private @NotBlank(message = "Tên quyền không được bỏ trống") String name;
    private String description;
    private String key;
}
