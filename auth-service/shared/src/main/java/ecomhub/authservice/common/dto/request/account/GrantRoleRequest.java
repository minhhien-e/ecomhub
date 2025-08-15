package ecomhub.authservice.common.dto.request.account;

import java.util.UUID;

public record GrantRoleRequest(UUID accountId, UUID roleId) {
}
