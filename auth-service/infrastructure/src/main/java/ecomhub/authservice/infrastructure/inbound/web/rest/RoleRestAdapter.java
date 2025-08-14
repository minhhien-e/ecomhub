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
import java.util.stream.Collectors;

import static ecomhub.authservice.infrastructure.inbound.web.mapper.RoleRequestMapper.toCommand;
import static ecomhub.authservice.infrastructure.inbound.web.mapper.RoleRequestMapper.toQuery;


@RestController
@RequestMapping("/api/v1/auth/role")
@RequiredArgsConstructor
public class RoleRestAdapter {
    private final ICommandBus commandBus;
    private final IQueryBus queryBus;

    @PreAuthorize("hasAuthority('role.create')")
    @PostMapping()
    public ResponseEntity<?> addRole(@RequestBody AddRoleRequest request) {
        commandBus.dispatch(toCommand(request));
        return ResponseEntity.ok(ApiResponse.success(null, "Thêm vai trò thành công"));
    }

    @PreAuthorize("hasAuthority('role.delete')")
    @DeleteMapping("/{roleId}")
    public ResponseEntity<?> deleteRole(@PathVariable UUID roleId, @RequestAttribute("accountId") UUID accountId) {
        var request = new DeleteRoleRequest(roleId);
        var input = toCommand(request, roleId);
        commandBus.dispatch(input);
        return ResponseEntity.ok(ApiResponse.success(null, "Xóa vai trò thành công"));
    }

    //region Update
    @PreAuthorize("hasAuthority('role.edit')")
    @PatchMapping("/{roleId}/name")
    public ResponseEntity<?> updateName(@PathVariable UUID roleId, @RequestBody String newName, @RequestAttribute("accountId") UUID accountId) {
        var request = new UpdateNameRoleRequest(newName);
        var input = toCommand(request, roleId, accountId);
        commandBus.dispatch(input);
        return ResponseEntity.ok(ApiResponse.success(null, "Thay đổi tên vai trò thành công"));
    }

    @PreAuthorize("hasAuthority('role.edit')")
    @PatchMapping("/{roleId}/level")
    public ResponseEntity<?> updateLevel(@PathVariable UUID roleId, @RequestBody int newLevel, @RequestAttribute("accountId") UUID accountId) {
        var request = new UpdateLevelRoleRequest(newLevel);
        var input = toCommand(request, roleId, accountId);
        commandBus.dispatch(input);
        return ResponseEntity.ok(ApiResponse.success(null, "Thay đổi cấp độ vai trò thành công"));
    }

    @PreAuthorize("hasAuthority('role.edit')")
    @PatchMapping("/{roleId}/description")
    public ResponseEntity<?> updateDescription(@PathVariable UUID roleId, @RequestBody String newDescription, @RequestAttribute("accountId") UUID accountId) {
        var request = new UpdateDescriptionRoleRequest(newDescription);
        var input = toCommand(request, roleId, accountId);
        commandBus.dispatch(input);
        return ResponseEntity.ok(ApiResponse.success(null, "Thay đổi miêu tả vai trò thành công"));
    }

    //endregion
    //region Read
    @PreAuthorize("hasAuthority('role.read')")
    @GetMapping
    public ResponseEntity<?> getAll() {
        var request = new GetAllRoleRequest();
        var query = toQuery(request);
        var result = queryBus.dispatch(query);
        var response = result.stream().map(RoleDto::toResponse).toList();
        return ResponseEntity.ok(ApiResponse.success(response, "Lấy danh sách vai trò thành công"));
    }
    //endregion
}
