package ecomhub.authservice.infrastructure.outbound.persistence.repository.account.impl;

import ecomhub.authservice.common.exception.concrete.account.AccountNotFoundException;
import ecomhub.authservice.domain.repository.AccountRepositoryPort;
import ecomhub.authservice.domain.entity.Account;
import ecomhub.authservice.infrastructure.outbound.persistence.entity.AccountEntity;
import ecomhub.authservice.infrastructure.outbound.persistence.mapper.AccountMapper;
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
        AccountEntity accountEntity = AccountMapper.toEntity(account);
        accountJpaRepository.save(accountEntity);
    }


    @Override
    public boolean existsByIdentifier(String identifier) {
        return accountJpaRepository.exists(AccountSpecification.byIdentifier(identifier));
    }

    @Override
    public Optional<Account> findByIdentifier(String identifier) {
        Optional<AccountEntity> account = accountJpaRepository.findOne(AccountSpecification.byIdentifier(identifier));
        return account.map(AccountMapper::toDomain);
    }

    @Override
    public Account geById(UUID id) {
        var entity = accountJpaRepository.findById(id)
                .orElseThrow(AccountNotFoundException::new);
        return AccountMapper.toDomain(entity);
    }

    @Override
    public void assignRole(UUID accountId, UUID roleId) {
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

}
