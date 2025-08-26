package ecomhub.authservice.infrastructure.inbound.web.rest;

import ecomhub.authservice.application.dto.PermissionDto;
import ecomhub.authservice.application.port.bus.ICommandBus;
import ecomhub.authservice.application.port.bus.IQueryBus;
import ecomhub.authservice.common.dto.request.permisison.GetAllPermissionRequest;
import ecomhub.authservice.common.dto.request.permisison.UpdateDescriptionPermissionRequest;
import ecomhub.authservice.common.dto.request.permisison.UpdateNamePermissionRequest;
import ecomhub.authservice.common.dto.response.ApiResponse;
import ecomhub.authservice.common.dto.response.PermissionResponse;
import ecomhub.authservice.infrastructure.inbound.web.annotations.ErrorResponse;
import ecomhub.authservice.infrastructure.inbound.web.annotations.StandardApiResponses;
import ecomhub.authservice.infrastructure.inbound.web.annotations.SuccessfulResponse;
import ecomhub.authservice.infrastructure.inbound.web.mapper.PermissionRequestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth/permission")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('permission.add')")
@Tag(name = "Permission API", description = "Operations related to permission management")
public class PermissionRestAdapter {
    private final ICommandBus commandBus;
    private final IQueryBus queryBus;

    @Operation(summary = "Update permission name", description = "Update the name of a specific permission")
    @StandardApiResponses(
            successExample = @SuccessfulResponse(message = "Permission name updated successfully", data = Object.class),
            errorExamples = {
                    @ErrorResponse(statusCode = "400", code = "REQUIRED_FIELD_MISSING", message = "Permission name is required"),
                    @ErrorResponse(statusCode = "404", code = "RESOURCE_NOT_FOUND", message = "Sorry, we couldn't find the permission you are looking for"),
                    @ErrorResponse(statusCode = "403", code = "FORBIDDEN", message = "You do not have sufficient permissions to update permission names")
            }
    )
    @PatchMapping("/{permissionId}/name")
    public ResponseEntity<?> updateName(@PathVariable UUID permissionId, @RequestBody String newName) {
        var request = new UpdateNamePermissionRequest(permissionId, newName);
        var command = PermissionRequestMapper.toCommand(request);
        commandBus.dispatch(command);
        return ResponseEntity.ok(ApiResponse.success(null, "Permission name updated successfully"));
    }

    @Operation(summary = "Update permission description", description = "Update the description of a specific permission")
    @StandardApiResponses(
            successExample = @SuccessfulResponse(message = "Permission description updated successfully", data = Object.class),
            errorExamples = {
                    @ErrorResponse(statusCode = "400", code = "REQUIRED_FIELD_MISSING", message = "Permission description is required"),
                    @ErrorResponse(statusCode = "404", code = "RESOURCE_NOT_FOUND", message = "Sorry, we couldn't find the permission you are looking for"),
                    @ErrorResponse(statusCode = "403", code = "FORBIDDEN", message = "You do not have sufficient permissions to update permission descriptions")
            }
    )
    @PatchMapping("/{permissionId}/description")
    public ResponseEntity<?> updateDescription(@PathVariable UUID permissionId, @RequestBody String newDescription) {
        var request = new UpdateDescriptionPermissionRequest(permissionId, newDescription);
        var command = PermissionRequestMapper.toCommand(request);
        commandBus.dispatch(command);
        return ResponseEntity.ok(ApiResponse.success(null, "Permission description updated successfully"));
    }

    @Operation(summary = "Get all permissions", description = "Retrieve all available permissions in the system")
    @StandardApiResponses(
            successExample = @SuccessfulResponse(message = "Permissions retrieved successfully", data = PermissionResponse.class, isList = true),
            errorExamples = {
                    @ErrorResponse(statusCode = "403", code = "FORBIDDEN", message = "You do not have sufficient permissions to view permissions")
            }
    )
    @PreAuthorize("hasAuthority('permission.read')")
    @GetMapping
    public ResponseEntity<?> getAllPermission() {
        var request = new GetAllPermissionRequest();
        var query = PermissionRequestMapper.toQuery(request);
        var result = queryBus.dispatch(query);
        var response = result.stream().map(PermissionDto::toResponse).toList();
        return ResponseEntity.ok(ApiResponse.success(response, "Permissions retrieved successfully"));
    }
}
