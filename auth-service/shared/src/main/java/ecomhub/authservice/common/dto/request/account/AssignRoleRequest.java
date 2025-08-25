package ecomhub.authservice.common.dto.request.account;

import java.util.UUID;

public record AssignRoleRequest(UUID accountId, UUID roleId) {
}
