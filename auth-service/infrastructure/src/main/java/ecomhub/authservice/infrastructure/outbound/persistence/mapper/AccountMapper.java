package ecomhub.authservice.infrastructure.outbound.persistence.mapper;

import ecomhub.authservice.domain.entity.Account;
import ecomhub.authservice.domain.entity.Role;
import ecomhub.authservice.domain.valueobject.Password;
import ecomhub.authservice.domain.valueobject.name.Username;
import ecomhub.authservice.infrastructure.outbound.persistence.entity.AccountEntity;
import ecomhub.authservice.infrastructure.outbound.persistence.entity.AccountRoleEntity;
import ecomhub.authservice.infrastructure.outbound.persistence.entity.RoleEntity;
import ecomhub.authservice.infrastructure.outbound.persistence.entity.id.AccountRoleId;

import java.util.Set;
import java.util.stream.Collectors;

public class AccountMapper {
    public static AccountEntity toEntity(Account account) {
        return AccountEntity.builder()
                .id(account.getId())
                .username(account.getUsername().map(Username::getValue).orElse(null))
                .email(account.getEmail().getValue())
                .phoneNumber(account.getPhoneNumber().getValue())
                .hashedPassword(account.getHashedPassword().map(Password::getHashedValue).orElse(null))
                .active(account.isActive())
                .provider(account.getProvider().getValue())
                .accountRoles(convertRoleToEntities(account))
                .build();
    }

    public static Account toDomain(AccountEntity accountEntity) {
        return new Account(accountEntity.getId(),
                accountEntity.getEmail(),
                accountEntity.getUsername(),
                accountEntity.getPhoneNumber(),
                accountEntity.getHashedPassword(),
                accountEntity.getProvider(),
                accountEntity.isActive(),
                convertRoleToDomain(accountEntity.getAccountRoles()));
    }

    private static Set<Role> convertRoleToDomain(Set<AccountRoleEntity> roles) {
        return roles
                .stream()
                .map(entity -> RoleMapper.toDomain(entity.getRole()))
                .collect(java.util.stream.Collectors.toSet());
    }

    private static Set<AccountRoleEntity> convertRoleToEntities(Account account) {
        return account.getRoles()
                .stream().map(role -> {
                    AccountRoleEntity accountRoleEntity = new AccountRoleEntity();
                    accountRoleEntity.setId(new AccountRoleId(account.getId(), role.getId()));
                    accountRoleEntity.setRole(RoleEntity.builder().id((role.getId())).build());
                    accountRoleEntity.setAccount(AccountEntity.builder().id(account.getId()).build());
                    return accountRoleEntity;
                })
                .collect(Collectors.toSet());
    }
}
