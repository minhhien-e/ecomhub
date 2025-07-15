package ecomhub.authservice.infrastructure.data.persistence.mapper;

import ecomhub.authservice.domain.entity.Account;
import ecomhub.authservice.infrastructure.data.persistence.entity.AccountEntity;
import ecomhub.authservice.infrastructure.data.persistence.entity.AccountRoleEntity;
import ecomhub.authservice.infrastructure.data.persistence.entity.RoleEntity;
import ecomhub.authservice.infrastructure.data.persistence.entity.id.AccountRoleId;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class AccountPersistenceMapper {
    public static AccountEntity toEntity(Account account) {
        return AccountEntity.builder()
                .id(account.getId())
                .username(account.getUsername().orElse(null))
                .email(account.getEmail())
                .phoneNumber(account.getPhoneNumber())
                .passwordHash(account.getPasswordHash().orElse(null))
                .active(account.isActive())
                .provider(account.getProvider())
                .accountRoles(convertRoleToEntities(account))
                .build();
    }

    public static Account toDomain(AccountEntity accountEntity) {
        return new Account(accountEntity.getId(),
                accountEntity.getEmail(),
                accountEntity.getUsername(),
                accountEntity.getPhoneNumber(),
                accountEntity.getPasswordHash(),
                accountEntity.getProvider(),
                accountEntity.isActive(),
                convertRoleToIds(accountEntity.getAccountRoles()));
    }

    /**
     * Chuyển RoleName thành id
     */
    private static Set<UUID> convertRoleToIds(Set<AccountRoleEntity> roles) {
        return roles
                .stream()
                .map(accountRole -> accountRole.getRole().getId())
                .collect(java.util.stream.Collectors.toSet());
    }

    private static Set<AccountRoleEntity> convertRoleToEntities(Account account) {
        return account.getRoleIds()
                .stream().map(id -> {
                    AccountRoleEntity accountRoleEntity = new AccountRoleEntity();
                    accountRoleEntity.setId(new AccountRoleId(account.getId(), id));
                    accountRoleEntity.setRole(RoleEntity.builder().id(id).build());
                    accountRoleEntity.setAccount(AccountEntity.builder().id(account.getId()).build());
                    return accountRoleEntity;
                })
                .collect(Collectors.toSet());
    }
}
