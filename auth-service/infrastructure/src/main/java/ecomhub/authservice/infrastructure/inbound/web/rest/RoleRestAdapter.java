package ecomhub.authservice.infrastructure.inbound.web.rest;

import ecomhub.authservice.application.dto.RoleDto;
import ecomhub.authservice.application.port.bus.ICommandBus;
import ecomhub.authservice.application.port.bus.IQueryBus;
import ecomhub.authservice.common.dto.request.role.*;
import ecomhub.authservice.common.dto.response.ApiResponse;
import ecomhub.authservice.common.dto.response.RoleResponse;
import ecomhub.authservice.infrastructure.inbound.web.annotations.ErrorResponse;
import ecomhub.authservice.infrastructure.inbound.web.annotations.StandardApiResponses;
import ecomhub.authservice.infrastructure.inbound.web.annotations.SuccessfulResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static ecomhub.authservice.infrastructure.inbound.web.mapper.RoleRequestMapper.toCommand;
import static ecomhub.authservice.infrastructure.inbound.web.mapper.RoleRequestMapper.toQuery;

@RestController
@RequestMapping("/api/v1/auth/role")
@RequiredArgsConstructor
@Tag(name = "Role API", description = "Operations related to role management")
public class RoleRestAdapter {
    private final ICommandBus commandBus;
    private final IQueryBus queryBus;

    @Operation(summary = "Add new role", description = "Create a new role in the system")
    @StandardApiResponses(
            successExample = @SuccessfulResponse(message = "Add role successfully", data = Object.class),
            errorExamples = {
                    @ErrorResponse(statusCode = "400", code = "REQUIRED_FIELD_MISSING", message = "Role information is required"),
                    @ErrorResponse(statusCode = "409", code = "RESOURCE_ALREADY_EXISTS", message = "Role with this name already exists"),
                    @ErrorResponse(statusCode = "403", code = "FORBIDDEN", message = "You do not have sufficient permissions to create roles")
            }
    )
    @PreAuthorize("hasAuthority('role.create')")
    @PostMapping
    public ResponseEntity<?> add(@RequestBody AddRoleRequest request, @RequestAttribute("accountId") UUID accountId) {
        commandBus.dispatch(toCommand(request, accountId));
        return ResponseEntity.ok(ApiResponse.success(null, "Add role successfully"));
    }

    @Operation(summary = "Delete role", description = "Delete a specific role from the system")
    @StandardApiResponses(
            successExample = @SuccessfulResponse(message = "Delete role successfully", data = Object.class),
            errorExamples = {
                    @ErrorResponse(statusCode = "400", code = "REQUIRED_FIELD_MISSING", message = "Role ID is required"),
                    @ErrorResponse(statusCode = "404", code = "RESOURCE_NOT_FOUND", message = "Sorry, we couldn't find the role you are looking for"),
                    @ErrorResponse(statusCode = "403", code = "FORBIDDEN", message = "You do not have sufficient permissions to delete roles")
            }
    )
    @PreAuthorize("hasAuthority('role.delete')")
    @DeleteMapping("/{roleId}")
    public ResponseEntity<?> delete(@PathVariable UUID roleId, @RequestAttribute("accountId") UUID accountId) {
        var request = new DeleteRoleRequest(roleId);
        var input = toCommand(request, accountId);
        commandBus.dispatch(input);
        return ResponseEntity.ok(ApiResponse.success(null, "Delete role successfully"));
    }

    @Operation(summary = "Update role name", description = "Update the name of a specific role",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The new name of the role",
                    required = true
            ))
    @StandardApiResponses(
            successExample = @SuccessfulResponse(message = "Update role name successfully", data = Object.class),
            errorExamples = {
                    @ErrorResponse(statusCode = "400", code = "REQUIRED_FIELD_MISSING", message = "Role name is required"),
                    @ErrorResponse(statusCode = "404", code = "RESOURCE_NOT_FOUND", message = "Sorry, we couldn't find the role you are looking for"),
                    @ErrorResponse(statusCode = "403", code = "FORBIDDEN", message = "You do not have sufficient permissions to update role names"),
                    @ErrorResponse(statusCode = "409", code = "DUPLICATE_ENTRY", message = "Role with this name already exists")
            }
    )
    @PreAuthorize("hasAuthority('role.update')")
    @PatchMapping("/{roleId}/name")
    public ResponseEntity<?> updateName(@PathVariable UUID roleId, @RequestBody String newName, @RequestAttribute("accountId") UUID accountId) {
        var request = new UpdateNameRoleRequest(roleId, newName);
        var input = toCommand(request, accountId);
        commandBus.dispatch(input);
        return ResponseEntity.ok(ApiResponse.success(null, "Update role name successfully"));
    }

    @Operation(summary = "Update role level", description = "Update the level of a specific role",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The new level of the role",
                    required = true
            ))
    @StandardApiResponses(
            successExample = @SuccessfulResponse(message = "Update role level successfully", data = Object.class),
            errorExamples = {
                    @ErrorResponse(statusCode = "400", code = "REQUIRED_FIELD_MISSING", message = "Role level is required"),
                    @ErrorResponse(statusCode = "400", code = "INVALID_VALUE", message = "Role level must be a valid integer"),
                    @ErrorResponse(statusCode = "404", code = "RESOURCE_NOT_FOUND", message = "Sorry, we couldn't find the role you are looking for"),
                    @ErrorResponse(statusCode = "403", code = "FORBIDDEN", message = "You do not have sufficient permissions to update role levels")
            }
    )
    @PreAuthorize("hasAuthority('role.update')")
    @PatchMapping("/{roleId}/level")
    public ResponseEntity<?> updateLevel(@PathVariable UUID roleId, @RequestBody int newLevel, @RequestAttribute("accountId") UUID accountId) {
        var request = new UpdateLevelRoleRequest(roleId, newLevel);
        var input = toCommand(request, accountId);
        commandBus.dispatch(input);
        return ResponseEntity.ok(ApiResponse.success(null, "Update role level successfully"));
    }

    @Operation(summary = "Update role description", description = "Update the description of a specific role",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The new description of the role",
                    required = true
            ))
    @StandardApiResponses(
            successExample = @SuccessfulResponse(message = "Update role description successfully", data = Object.class),
            errorExamples = {
                    @ErrorResponse(statusCode = "400", code = "REQUIRED_FIELD_MISSING", message = "Role description is required"),
                    @ErrorResponse(statusCode = "404", code = "RESOURCE_NOT_FOUND", message = "Sorry, we couldn't find the role you are looking for"),
                    @ErrorResponse(statusCode = "403", code = "FORBIDDEN", message = "You do not have sufficient permissions to update role descriptions")
            }
    )
    @PreAuthorize("hasAuthority('role.update')")
    @PatchMapping("/{roleId}/description")
    public ResponseEntity<?> updateDescription(@PathVariable UUID roleId,
                                               @RequestBody String newDescription, @RequestAttribute("accountId") UUID accountId) {
        var request = new UpdateDescriptionRoleRequest(roleId, newDescription);
        var input = toCommand(request, accountId);
        commandBus.dispatch(input);
        return ResponseEntity.ok(ApiResponse.success(null, "Update role description successfully"));
    }

    @Operation(summary = "Get all roles", description = "Retrieve all available roles in the system")
    @StandardApiResponses(
            successExample = @SuccessfulResponse(message = "Get all roles successfully", data = RoleResponse.class, isList = true),
            errorExamples = {
                    @ErrorResponse(statusCode = "403", code = "FORBIDDEN", message = "You do not have sufficient permissions to view roles")
            }
    )
    @PreAuthorize("hasAuthority('role.read')")
    @GetMapping
    public ResponseEntity<?> getAll() {
        var request = new GetAllRoleRequest();
        var query = toQuery(request);
        var result = queryBus.dispatch(query);
        var response = result.stream().map(RoleDto::toResponse).toList();
        return ResponseEntity.ok(ApiResponse.success(response, "Get all roles successfully"));
    }

    @Operation(summary = "Grant permission to role", description = "Grant a specific permission to a role")
    @StandardApiResponses(
            successExample = @SuccessfulResponse(message = "Grant permission successfully", data = Object.class),
            errorExamples = {
                    @ErrorResponse(statusCode = "400", code = "REQUIRED_FIELD_MISSING", message = "Permission information is required"),
                    @ErrorResponse(statusCode = "404", code = "RESOURCE_NOT_FOUND", message = "Sorry, we couldn't find the role or permission you are looking for"),
                    @ErrorResponse(statusCode = "403", code = "FORBIDDEN", message = "You do not have sufficient permissions to grant permissions to roles"),
                    @ErrorResponse(statusCode = "409", code = "DUPLICATE_ENTRY", message = "Permission is already assigned to this role")
            }
    )
    @PreAuthorize("hasAuthority('role.permission.grant')")
    @PutMapping("/{roleId}/grant/permission")
    public ResponseEntity<?> grantPermission(@PathVariable("roleId") UUID roleId, @RequestAttribute("accountId") UUID accountId, @RequestBody GrantPermissionRequest request) {
        var command = toCommand(request, roleId, accountId);
        commandBus.dispatch(command);
        return ResponseEntity.ok(ApiResponse.success(null, "Grant permission successfully"));
    }

    @Operation(summary = "Revoke permission from role", description = "Revoke a specific permission from a role")
    @StandardApiResponses(
            successExample = @SuccessfulResponse(message = "Revoke permission successfully", data = Object.class),
            errorExamples = {
                    @ErrorResponse(statusCode = "400", code = "REQUIRED_FIELD_MISSING", message = "Permission ID is required"),
                    @ErrorResponse(statusCode = "404", code = "RESOURCE_NOT_FOUND", message = "Sorry, we couldn't find the role or permission you are looking for"),
                    @ErrorResponse(statusCode = "403", code = "FORBIDDEN", message = "You do not have sufficient permissions to revoke permissions from roles")
            }
    )
    @PreAuthorize("hasAuthority('role.permission.revoke')")
    @DeleteMapping("/{roleId}/revoke/permission/{permissionId}")
    public ResponseEntity<?> revokePermission(@PathVariable("roleId") UUID roleId, @RequestAttribute("accountId") UUID accountId, @PathVariable("permissionId") UUID permissionId) {
        var request = new RevokePermissionRequest(permissionId);
        var command = toCommand(request, roleId, accountId);
        commandBus.dispatch(command);
        return ResponseEntity.ok(ApiResponse.success(null, "Revoke permission successfully"));
    }
}
