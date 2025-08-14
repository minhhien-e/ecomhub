package ecomhub.authservice.infrastructure.inbound.web.rest;

import ecomhub.authservice.application.port.bus.ICommandBus;
import ecomhub.authservice.application.port.bus.IQueryBus;
import ecomhub.authservice.common.dto.ApiResponse;
import ecomhub.authservice.infrastructure.inbound.web.dto.request.permisison.AddPermissionRequest;
import ecomhub.authservice.infrastructure.inbound.web.dto.request.permisison.DeletePermissionRequest;
import ecomhub.authservice.infrastructure.inbound.web.dto.request.permisison.UpdateNamePermissionRequest;
import ecomhub.authservice.infrastructure.inbound.web.mapper.PermissionRequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static ecomhub.authservice.infrastructure.inbound.web.mapper.PermissionRequestMapper.toCommand;

@RestController
@RequestMapping("/api/v1/auth/permissions")
@RequiredArgsConstructor
public class PermissionRestAdapter {
    private final ICommandBus commandBus;
    private final IQueryBus queryBus;

    @PreAuthorize("hasAuthority('permission.create')")
    @PostMapping()
    public ResponseEntity<?> addPermission(@RequestBody AddPermissionRequest request) {
        commandBus.dispatch(toCommand(request));
        return ResponseEntity.ok(ApiResponse.success(null, "Thêm quyền thành công"));
    }

    @PreAuthorize("hasAuthority('permission.delete')")
    @DeleteMapping("/{permissionId}/delete")
    public ResponseEntity<?> deletePermission(@PathVariable UUID permissionId, @RequestAttribute("accountId") UUID accountId) {
        var request = new DeletePermissionRequest(permissionId);
        var command = PermissionRequestMapper.toCommand(request, accountId);
        commandBus.dispatch(command);
        return ResponseEntity.ok(ApiResponse.success(null, "Xóa quyền thành công"));
    }

    @PreAuthorize("hasAuthority('permission.edit')")
    @PatchMapping("/{permissionId}/name")
    public ResponseEntity<?> updateName(@PathVariable UUID permissionId, @RequestBody String newName, @RequestAttribute("accountId") UUID accountId) {
        var request = new UpdateNamePermissionRequest(newName);
        var command = PermissionRequestMapper.toCommand(request, permissionId, accountId);
        commandBus.dispatch(command);
        return ResponseEntity.ok(ApiResponse.success(null, "Thay đổi tên quyền thành công"));
    }

    @PreAuthorize("hasAuthority('permission.edit')")
    @PatchMapping("/{permissionId}/description")
    public ResponseEntity<?> updateDescription(@PathVariable UUID permissionId, @RequestBody String newDescription, @RequestAttribute("accountId") UUID accountId) {
        var request = new UpdateNamePermissionRequest(newDescription);
        var command = PermissionRequestMapper.toCommand(request, permissionId, accountId);
        commandBus.dispatch(command);
        return ResponseEntity.ok(ApiResponse.success(null, "Thay đổi miêu tả quyền thành công"));
    }
}
