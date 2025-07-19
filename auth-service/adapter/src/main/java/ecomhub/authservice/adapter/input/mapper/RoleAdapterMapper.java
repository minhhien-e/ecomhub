package ecomhub.authservice.adapter.input.mapper;

import ecomhub.authservice.adapter.input.request.AddRoleRequest;
import ecomhub.authservice.application.command.role.add.AddRoleCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleAdapterMapper {
    AddRoleCommand toCommand(AddRoleRequest request);
}
