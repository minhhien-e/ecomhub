package ecomhub.authservice.adapter.input.rest;

import ecomhub.authservice.adapter.input.mapper.RoleAdapterMapper;
import ecomhub.authservice.adapter.input.request.role.*;
import ecomhub.authservice.application.bus.ICommandBus;
import ecomhub.authservice.application.bus.IQueryBus;
import ecomhub.authservice.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class RoleRestAdapter {
    private final ICommandBus commandBus;
    private final IQueryBus queryBus;
    private final RoleAdapterMapper mapper;

    @PreAuthorize("hasAuthority('role.create')")
    @PostMapping("/role")
    public ResponseEntity<?> addRole(@RequestBody AddRoleRequest request) {
        commandBus.dispatch(mapper.toCommand(request));
        return ResponseEntity.ok(ApiResponse.success(null, "Thêm vai trò thành công"));
    }

    @PreAuthorize("hasAuthority('role.delete')")
    @DeleteMapping("/role/{roleId}")
    public ResponseEntity<?> deleteRole(@PathVariable UUID roleId, @RequestAttribute("accountId") UUID accountId) {
        var request = new DeleteRoleRequest(roleId);
        var input = mapper.toCommand(request);
        input.setRequesterId(accountId);
        commandBus.dispatch(input);
        return ResponseEntity.ok(ApiResponse.success(null, "Xóa vai trò thành công"));
    }

    @PreAuthorize("hasAuthority('role.edit')")
    @PatchMapping("/role/{roleId}/name")
    public ResponseEntity<?> updateName(@PathVariable UUID roleId, @RequestBody String newName, @RequestAttribute("accountId") UUID accountId) {
        var request = new UpdateNameRoleRequest(newName);
        var input = mapper.toCommand(request);
        input.setRequesterId(accountId);
        input.setRoleId(roleId);
        commandBus.dispatch(input);
        return ResponseEntity.ok(ApiResponse.success(null, "Thay đổi tên vai trò thành công"));
    }

    @PreAuthorize("hasAuthority('role.edit')")
    @PatchMapping("/role/{roleId}/level")
    public ResponseEntity<?> updateLevel(@PathVariable UUID roleId, @RequestBody int newLevel, @RequestAttribute("accountId") UUID accountId) {
        var request = new UpdateLevelRoleRequest(newLevel);
        var input = mapper.toCommand(request);
        input.setRequesterId(accountId);
        input.setRoleId(roleId);
        commandBus.dispatch(input);
        return ResponseEntity.ok(ApiResponse.success(null, "Thay đổi cấp độ vai trò thành công"));
    }

    @PreAuthorize("hasAuthority('role.edit')")
    @PatchMapping("/role/{roleId}/description")
    public ResponseEntity<?> updateDescription(@PathVariable UUID roleId, @RequestBody String newDescription, @RequestAttribute("accountId") UUID accountId) {
        var request = new UpdateDescriptionRoleRequest(newDescription);
        var input = mapper.toCommand(request);
        input.setRequesterId(accountId);
        input.setRoleId(roleId);
        commandBus.dispatch(input);
        return ResponseEntity.ok(ApiResponse.success(null, "Thay đổi miêu tả vai trò thành công"));
    }
}
