package ecomhub.authservice.adapter.input.mapper;

import ecomhub.authservice.adapter.input.request.role.AddRoleRequest;
import ecomhub.authservice.adapter.input.request.role.DeleteRoleRequest;
import ecomhub.authservice.application.command.role.add.AddRoleCommand;
import ecomhub.authservice.application.command.role.delete.DeleteRoleCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleAdapterMapper {
    AddRoleCommand toCommand(AddRoleRequest request);
    DeleteRoleCommand toCommand(DeleteRoleRequest request);
}
