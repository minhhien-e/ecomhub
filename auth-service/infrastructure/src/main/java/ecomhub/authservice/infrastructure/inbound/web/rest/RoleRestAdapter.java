package ecomhub.authservice.infrastructure.inbound.web.rest;

import ecomhub.authservice.application.dto.RoleDto;
import ecomhub.authservice.application.port.bus.ICommandBus;
import ecomhub.authservice.application.port.bus.IQueryBus;
import ecomhub.authservice.common.dto.request.role.*;
import ecomhub.authservice.common.dto.response.ApiResponse;
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
@PreAuthorize("hasAuthority('role.add')")
public class RoleRestAdapter {
    private final ICommandBus commandBus;
    private final IQueryBus queryBus;

    @PostMapping()
    public ResponseEntity<?> add(@RequestBody AddRoleRequest request, @RequestAttribute("accountId") UUID accountId) {
        commandBus.dispatch(toCommand(request, accountId));
        return ResponseEntity.ok(ApiResponse.success(null, "Add role successfully"));
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<?> delete(@PathVariable UUID roleId, @RequestAttribute("accountId") UUID accountId) {
        var request = new DeleteRoleRequest(roleId);
        var input = toCommand(request, accountId);
        commandBus.dispatch(input);
        return ResponseEntity.ok(ApiResponse.success(null, "Delete role successfully"));
    }

    @PatchMapping("/{roleId}/name")
    public ResponseEntity<?> updateName(@PathVariable UUID roleId, @RequestBody String newName, @RequestAttribute("accountId") UUID accountId) {
        var request = new UpdateNameRoleRequest(roleId,newName);
        var input = toCommand(request, accountId);
        commandBus.dispatch(input);
        return ResponseEntity.ok(ApiResponse.success(null, "Update role name successfully"));
    }

    @PatchMapping("/{roleId}/level")
    public ResponseEntity<?> updateLevel(@PathVariable UUID roleId, @RequestBody int newLevel, @RequestAttribute("accountId") UUID accountId) {
        var request = new UpdateLevelRoleRequest(roleId,newLevel);
        var input = toCommand(request, accountId);
        commandBus.dispatch(input);
        return ResponseEntity.ok(ApiResponse.success(null, "Update role level successfully"));
    }

    @PatchMapping("/{roleId}/description")
    public ResponseEntity<?> updateDescription(@PathVariable UUID roleId, @RequestBody String newDescription, @RequestAttribute("accountId") UUID accountId) {
        var request = new UpdateDescriptionRoleRequest(roleId,newDescription);
        var input = toCommand(request, accountId);
        commandBus.dispatch(input);
        return ResponseEntity.ok(ApiResponse.success(null, "Update role description successfully"));
    }

    @PreAuthorize("hasAuthority('role.read')")
    @GetMapping
    public ResponseEntity<?> getAll() {
        var request = new GetAllRoleRequest();
        var query = toQuery(request);
        var result = queryBus.dispatch(query);
        var response = result.stream().map(RoleDto::toResponse).toList();
        return ResponseEntity.ok(ApiResponse.success(response, "Get all roles successfully"));
    }

    @PreAuthorize("hasAuthority('role.permission.grant')")
    @PutMapping("/{roleId}/grant/permission")
    public ResponseEntity<?> grantPermission(@PathVariable("roleId") UUID roleId, @RequestAttribute("accountId") UUID accountId, @RequestBody GrantPermissionRequest request) {
        var command = toCommand(request, roleId, accountId);
        commandBus.dispatch(command);
        return ResponseEntity.ok(ApiResponse.success(null, "Grant permission successfully"));
    }

    @PreAuthorize("hasAuthority('role.permission.grant')")
    @DeleteMapping("/{roleId}/revoke/permission/{permissionId}")
    public ResponseEntity<?> revokePermission(@PathVariable("roleId") UUID roleId, @RequestAttribute("accountId") UUID accountId, @PathVariable("permissionId") UUID permissionId) {
        var request = new RevokePermissionRequest(permissionId);
        var command = toCommand(request, roleId, accountId);
        commandBus.dispatch(command);
        return ResponseEntity.ok(ApiResponse.success(null, "Revoke permission successfully"));
    }
}
