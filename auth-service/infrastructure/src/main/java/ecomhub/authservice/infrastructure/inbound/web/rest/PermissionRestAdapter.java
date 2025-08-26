package ecomhub.authservice.infrastructure.inbound.web.rest;

import ecomhub.authservice.application.dto.PermissionDto;
import ecomhub.authservice.application.port.bus.ICommandBus;
import ecomhub.authservice.application.port.bus.IQueryBus;
import ecomhub.authservice.common.dto.request.permisison.GetAllPermissionRequest;
import ecomhub.authservice.common.dto.request.permisison.UpdateDescriptionPermissionRequest;
import ecomhub.authservice.common.dto.request.permisison.UpdateNamePermissionRequest;
import ecomhub.authservice.common.dto.response.ApiResponse;
import ecomhub.authservice.infrastructure.inbound.web.mapper.PermissionRequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth/permission")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('permission.add')")
public class PermissionRestAdapter {
    private final ICommandBus commandBus;
    private final IQueryBus queryBus;

    @PatchMapping("/{permissionId}/name")
    public ResponseEntity<?> updateName(@PathVariable UUID permissionId, @RequestBody String newName) {
        var request = new UpdateNamePermissionRequest(permissionId, newName);
        var command = PermissionRequestMapper.toCommand(request);
        commandBus.dispatch(command);
        return ResponseEntity.ok(ApiResponse.success(null, "Permission name updated successfully"));
    }

    @PatchMapping("/{permissionId}/description")
    public ResponseEntity<?> updateDescription(@PathVariable UUID permissionId, @RequestBody String newDescription) {
        var request = new UpdateDescriptionPermissionRequest(permissionId, newDescription);
        var command = PermissionRequestMapper.toCommand(request);
        commandBus.dispatch(command);
        return ResponseEntity.ok(ApiResponse.success(null, "Permission description updated successfully"));
    }

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
