package ecomhub.authservice.adapter.mapper;

import ecomhub.authservice.adapter.dto.request.AddRoleRequest;
import ecomhub.authservice.application.command.AddRoleCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    AddRoleCommand toCommand(AddRoleRequest request);
}
