package ecomhub.authservice.infrastructure.inbound.web.rest;

import ecomhub.authservice.application.port.bus.ICommandBus;
import ecomhub.authservice.application.port.bus.IQueryBus;
import ecomhub.authservice.common.dto.request.account.AssignRoleRequest;
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
        return ResponseEntity.ok(ApiResponse.success(null, "Register account successfully"));
    }

    @PreAuthorize("hasAuthority('account.role.assign')")
    @PutMapping("/{accountId}/role/assign")
    public ResponseEntity<?> grantRole(@RequestBody UUID roleId, @RequestAttribute("accountId") UUID requesterId, @PathVariable("accountId") UUID accountId) {
        var request = new AssignRoleRequest(roleId, accountId);
        commandBus.dispatch(toCommand(request, requesterId));
        return ResponseEntity.ok(ApiResponse.success(null, "Assign role successfully"));
    }

    @PreAuthorize("hasAuthority('account.role.assign')")
    @DeleteMapping("/{accountId}/role/{roleId}/revoke")
    public ResponseEntity<?> revokeRole(@PathVariable("roleId") UUID roleId, @RequestAttribute("accountId") UUID requesterId, @PathVariable("accountId") UUID accountId) {
        var request = new RevokeRoleRequest(roleId, accountId);
        commandBus.dispatch(toCommand(request, requesterId));
        return ResponseEntity.ok(ApiResponse.success(null, "Revoke role successfully"));
    }

}
