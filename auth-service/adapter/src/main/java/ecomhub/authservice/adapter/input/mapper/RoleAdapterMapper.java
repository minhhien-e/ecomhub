
package ecomhub.authservice.adapter.input.mapper;

import ecomhub.authservice.adapter.input.request.role.*;
import ecomhub.authservice.application.command.role.add.AddRoleCommand;
import ecomhub.authservice.application.command.role.delete.DeleteRoleCommand;
import ecomhub.authservice.application.command.role.update.description.UpdateDescriptionRoleCommand;
import ecomhub.authservice.application.command.role.update.level.UpdateLevelRoleCommand;
import ecomhub.authservice.application.command.role.update.name.UpdateNameRoleCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleAdapterMapper {
    AddRoleCommand toCommand(AddRoleRequest request);
    DeleteRoleCommand toCommand(DeleteRoleRequest request);
    UpdateDescriptionRoleCommand toCommand(UpdateDescriptionRoleRequest request);
    UpdateNameRoleCommand toCommand(UpdateNameRoleRequest request);
    UpdateLevelRoleCommand toCommand(UpdateLevelRoleRequest request);
}
