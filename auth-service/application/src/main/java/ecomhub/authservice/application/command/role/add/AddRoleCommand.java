package ecomhub.authservice.application.command.role.add;

import ecomhub.authservice.application.command.abstracts.ICommand;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddRoleCommand implements ICommand {
    @NotBlank
    private String name;
    private String description;
    @Min(value = 0,message = "Cấp độ vai trò phải là số dương")
    private int level;
    @NotEmpty(message = "Quyền không được bỏ trống")
    private Set<@NotNull(message = "Khóa quyền không không được bỏ trống") String> permissionKeys;
}
