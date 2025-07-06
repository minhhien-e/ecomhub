package ecomhub.authservice.infrastructure.persistence.repository.account.impl;

import ecomhub.authservice.domain.model.Account;
import ecomhub.authservice.domain.repository.AccountRepository;
import ecomhub.authservice.infrastructure.mapper.AccountMapper;
import ecomhub.authservice.infrastructure.persistence.entity.AccountEntity;
import ecomhub.authservice.infrastructure.persistence.entity.AccountRoleEntity;
import ecomhub.authservice.infrastructure.persistence.entity.id.AccountRoleId;
import ecomhub.authservice.infrastructure.persistence.repository.account.AccountJpaRepository;
import ecomhub.authservice.infrastructure.persistence.repository.specification.AccountSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {
    private final AccountJpaRepository accountJpaRepository;

    @Override
    public void save(Account account) {
        AccountEntity accountEntity = accountJpaRepository.save(AccountMapper.toEntity(account));
        Set<AccountRoleId> accountRoleIds = buildAccountRoleIds(account.getRoleIds(), accountEntity.getId());
        Set<AccountRoleEntity> accountRoleEntities = buildAccountRoles(accountRoleIds);
        accountEntity.setAccountRoles(accountRoleEntities);
        accountJpaRepository.save(accountEntity);
    }

    @Override
    public Account findByIdentifier(String identifier) {
        Optional<AccountEntity> account = accountJpaRepository.findOne(AccountSpecification.byIdentifier(identifier));
        return account.map(AccountMapper::toDomain).orElse(null);
    }

    @Override
    public boolean isAccountExists(String identifier) {
        return accountJpaRepository.exists(AccountSpecification.byIdentifier(identifier));
    }

    /**
     * Tạo danh sách các AccountRoleId từ tập hợp roleId và accountId
     *
     * @param roleIds   Tập hợp ID của các role
     * @param accountId ID của tài khoản
     * @return Tập hợp các AccountRoleId dùng để tạo id cho AccountRole
     */
    private Set<AccountRoleId> buildAccountRoleIds(Set<UUID> roleIds, UUID accountId) {
        return roleIds.stream()
                .map(roleId -> new AccountRoleId(accountId, roleId))
                .collect(Collectors.toSet());
    }

    /**
     * Tạo danh sách các AccountRoles từ tập hợp accountRoleIds
     *
     * @param accountRoleIds   Tập hợp ID của các account role
     * @return Tập hợp các AccountRole dùng để ánh xạ quan hệ account-role
     */
    private Set<AccountRoleEntity> buildAccountRoles(Set<AccountRoleId> accountRoleIds) {
        return accountRoleIds.stream()
                .map(id -> AccountRoleEntity.builder().id(id).build())
                .collect(Collectors.toSet());
    }
}
