package ecomhub.authservice.adapter.input.mapper;

import ecomhub.authservice.adapter.input.request.AddRoleRequest;
import ecomhub.authservice.application.usecase.role.addrole.AddRoleCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleRequestMapper {
    AddRoleCommand toCommand(AddRoleRequest request);
}
