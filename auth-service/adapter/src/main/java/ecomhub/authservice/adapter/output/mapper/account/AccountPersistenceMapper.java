package ecomhub.authservice.adapter.output.mapper.account;

import ecomhub.authservice.adapter.output.persistence.entity.AccountEntity;
import ecomhub.authservice.adapter.output.persistence.entity.AccountRoleEntity;
import ecomhub.authservice.domain.entities.Account;

import java.util.Set;
import java.util.UUID;

public class AccountPersistenceMapper {
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
