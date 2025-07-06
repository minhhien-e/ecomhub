package ecomhub.authservice.infrastructure.mapper;

import ecomhub.authservice.domain.model.Account;
import ecomhub.authservice.infrastructure.persistence.entity.AccountEntity;
import ecomhub.authservice.infrastructure.persistence.entity.AccountRoleEntity;

import java.util.Set;
import java.util.UUID;

public class AccountMapper {
    public static AccountEntity toEntity(Account account) {
        return AccountEntity.builder()
                .id(account.getId())
                .username(account.getUsername())
                .email(account.getEmail())
                .phoneNumber(account.getPhoneNumber())
                .passwordHash(account.getPasswordHash())
                .active(account.isActive())
                .provider(account.getProvider())
                .build();
    }

    public static Account toDomain(AccountEntity accountEntity) {
        return Account.builder()
                .id(accountEntity.getId())
                .username(accountEntity.getUsername())
                .email(accountEntity.getEmail())
                .phoneNumber(accountEntity.getPhoneNumber())
                .passwordHash(accountEntity.getPasswordHash())
                .active(accountEntity.isActive())
                .provider(accountEntity.getProvider())
                .roleIds(convertRoleToIds(accountEntity.getAccountRoles()))
                .build();
    }

    /**
     * Chuyển Role thành id
     */
    private static Set<UUID> convertRoleToIds(Set<AccountRoleEntity> roles) {
        return roles
                .stream()
                .map(accountRole -> accountRole.getRole().getId())
                .collect(java.util.stream.Collectors.toSet());
    }
}
