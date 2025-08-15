package ecomhub.authservice.infrastructure.outbound.persistence.repository.account.impl;

import ecomhub.authservice.domain.entity.Role;
import ecomhub.authservice.domain.repository.AccountRepositoryPort;
import ecomhub.authservice.domain.entity.Account;
import ecomhub.authservice.infrastructure.outbound.persistence.entity.AccountEntity;
import ecomhub.authservice.infrastructure.outbound.persistence.converter.AccountConverter;
import ecomhub.authservice.infrastructure.outbound.persistence.entity.AccountRoleEntity;
import ecomhub.authservice.infrastructure.outbound.persistence.entity.RoleEntity;
import ecomhub.authservice.infrastructure.outbound.persistence.entity.id.AccountRoleId;
import ecomhub.authservice.infrastructure.outbound.persistence.repository.account.AccountJpaRepository;
import ecomhub.authservice.infrastructure.outbound.persistence.repository.account.AccountRoleJpaRepository;
import ecomhub.authservice.infrastructure.outbound.persistence.specification.AccountSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AccountRepository implements AccountRepositoryPort {
    private final AccountJpaRepository accountJpaRepository;
    private final AccountRoleJpaRepository accountRoleJpaRepository;

    @Override
    public void save(Account account) {
        AccountEntity accountEntity = AccountConverter.toEntity(account);
        accountJpaRepository.save(accountEntity);
    }


    @Override
    public boolean existsByIdentifier(String identifier) {
        return accountJpaRepository.exists(AccountSpecification.byIdentifier(identifier));
    }

    //region find
    @Override
    public Optional<Account> findByIdentifier(String identifier) {
        Optional<AccountEntity> account = accountJpaRepository.findOne(AccountSpecification.byIdentifier(identifier));
        return account.map(AccountConverter::toDomain);
    }

    @Override
    public Optional<Account> findById(UUID id) {
        return accountJpaRepository.findById(id).map(AccountConverter::toDomain);
    }

    //endregion
    //region Role Management
    @Override
    public void grantRole(UUID accountId, UUID roleId) {
        accountRoleJpaRepository.save(AccountRoleEntity.builder()
                .id(new AccountRoleId(accountId, roleId))
                .account(AccountEntity.builder().id(accountId).build())
                .role(RoleEntity.builder().id(roleId).build())
                .build());
    }

    @Override
    public void revokeRole(UUID accountId, UUID roleId) {
        accountRoleJpaRepository.delete(AccountRoleEntity.builder()
                .id(new AccountRoleId(accountId, roleId))
                .account(AccountEntity.builder().id(accountId).build())
                .role(RoleEntity.builder().id(roleId).build())
                .build());
    }
    //endregion

}
