package ecomhub.authservice.infrastructure.inbound.web.rest;

import ecomhub.authservice.application.port.bus.ICommandBus;
import ecomhub.authservice.application.port.bus.IQueryBus;
import ecomhub.authservice.common.dto.request.account.GrantRoleRequest;
import ecomhub.authservice.common.dto.request.account.RegisterBasicRequest;
import ecomhub.authservice.common.dto.request.account.RevokeRoleRequest;
import ecomhub.authservice.common.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static ecomhub.authservice.infrastructure.inbound.web.mapper.AccountRequestMapper.toCommand;

@RestController
@RequestMapping("/api/v1/auth/account")
@RequiredArgsConstructor
public class AccountRestAdapter {
    private final ICommandBus commandBus;
    private final IQueryBus queryBus;

    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@RequestBody RegisterBasicRequest request) {
        commandBus.dispatch(toCommand(request));
        return ResponseEntity.ok(ApiResponse.success(null, "Đăng ký thành công"));
    }

    @PreAuthorize("hasAuthority('account.role.grant')")
    @PutMapping("/{accountId}/role/grant")
    public ResponseEntity<?> grantRole(@RequestBody UUID roleId, @RequestAttribute("accountId") UUID requesterId, @PathVariable("accountId") UUID accountId) {
        var request = new GrantRoleRequest(roleId, accountId);
        commandBus.dispatch(toCommand(request, accountId));
        return ResponseEntity.ok(ApiResponse.success(null, "Gán quyền thành công"));
    }

    @PreAuthorize("hasAuthority('account.role.revoke')")
    @DeleteMapping("/role/{roleId}/revoke")
    public ResponseEntity<?> revokeRole(@PathVariable("roleId") UUID roleId, @RequestAttribute("accountId") UUID accountId) {
        var request = new RevokeRoleRequest(roleId, accountId);
        commandBus.dispatch(toCommand(request, accountId));
        return ResponseEntity.ok(ApiResponse.success(null, "Hủy quyền thành công"));
    }

}
