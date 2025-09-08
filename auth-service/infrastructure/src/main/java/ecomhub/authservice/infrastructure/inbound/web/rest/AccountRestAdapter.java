package ecomhub.authservice.infrastructure.inbound.web.rest;

import ecomhub.authservice.application.port.bus.ICommandBus;
import ecomhub.authservice.application.port.bus.IQueryBus;
import ecomhub.authservice.common.dto.request.account.AssignRoleRequest;
import ecomhub.authservice.common.dto.request.account.RevokeRoleRequest;
import ecomhub.authservice.common.dto.response.ApiResponse;
import ecomhub.authservice.infrastructure.inbound.web.annotations.ErrorResponse;
import ecomhub.authservice.infrastructure.inbound.web.annotations.StandardApiResponses;
import ecomhub.authservice.infrastructure.inbound.web.annotations.SuccessfulResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static ecomhub.authservice.infrastructure.inbound.web.mapper.AccountRequestMapper.toCommand;

@RestController
@RequestMapping("/api/v1/auth/account")
@RequiredArgsConstructor
@Tag(name = "Account API", description = "Endpoints related to account management")
public class AccountRestAdapter {
    private final ICommandBus commandBus;
    private final IQueryBus queryBus;

    @Operation(summary = "Assign role to account", description = "Assign role to account")
    @StandardApiResponses(
            successExample = @SuccessfulResponse(message = "Assign role to account", data = Object.class),
            errorExamples = {
                    @ErrorResponse(statusCode = "400", code = "REQUIRED_FIELD_MISSING", message = "Please select a role to assign to the account"),
                    @ErrorResponse(statusCode = "404", code = "RESOURCE_NOT_FOUND", message = "Sorry, we couldn't find the role you are looking for"),
                    @ErrorResponse(statusCode = "403", code = "FORBIDDEN", message = "You do not have sufficient permissions to assign a role to the account."),
                    @ErrorResponse(statusCode = "409", code = "DUPLICATE_ENTRY", message = "The role 'admin' already exists.")
            }
    )
    @PreAuthorize("hasAuthority('account.role.assign')")
    @PutMapping("/{accountId}/role/assign")
    public ResponseEntity<?> grantRole(@RequestBody UUID roleId, @RequestAttribute("accountId") UUID requesterId, @PathVariable("accountId") UUID accountId) {
        var request = new AssignRoleRequest(accountId, roleId);
        commandBus.dispatch(toCommand(request, requesterId));
        return ResponseEntity.ok(ApiResponse.success(null, "Assign role successfully"));
    }

    @Operation(summary = "Revoke role from account", description = "Revoke role from account")
    @StandardApiResponses(
            successExample = @SuccessfulResponse(message = "Assign role to account", data = Object.class),
            errorExamples = {
                    @ErrorResponse(statusCode = "400", code = "REQUIRED_FIELD_MISSING", message = "Please select a role to assign to the account"),
                    @ErrorResponse(statusCode = "404", code = "RESOURCE_NOT_FOUND", message = "Sorry, we couldn't find the role you are looking for"),
                    @ErrorResponse(statusCode = "403", code = "FORBIDDEN", message = "You do not have sufficient permissions to revoke a role from the account."),
                    @ErrorResponse(statusCode = "409", code = "DUPLICATE_ENTRY", message = "The role 'admin' was not found.")
            }
    )
    @PreAuthorize("hasAuthority('account.role.revoke')")
    @DeleteMapping("/{accountId}/role/{roleId}/revoke")
    public ResponseEntity<?> revokeRole(@PathVariable("roleId") UUID roleId, @RequestAttribute("accountId") UUID requesterId, @PathVariable("accountId") UUID accountId) {
        var request = new RevokeRoleRequest(roleId, accountId);
        commandBus.dispatch(toCommand(request, requesterId));
        return ResponseEntity.ok(ApiResponse.success(null, "Revoke role successfully"));
    }

}
