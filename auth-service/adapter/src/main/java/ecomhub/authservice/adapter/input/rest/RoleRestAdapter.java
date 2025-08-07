package ecomhub.authservice.adapter.input.rest;

import ecomhub.authservice.adapter.input.mapper.RoleAdapterMapper;
import ecomhub.authservice.adapter.input.request.role.AddRoleRequest;
import ecomhub.authservice.adapter.input.request.role.DeleteRoleRequest;
import ecomhub.authservice.application.bus.ICommandBus;
import ecomhub.authservice.application.bus.IQueryBus;
import ecomhub.authservice.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
}
