package ecomhub.authservice.common.dto.request.account;

import java.util.UUID;

public record RevokeRoleRequest(UUID roleId, UUID accountId) {
}
