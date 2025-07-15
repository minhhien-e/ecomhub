package ecomhub.authservice.adapter.output.repository;

import ecomhub.authservice.application.port.repository.AccountRepositoryPort;
import ecomhub.authservice.domain.entity.Account;
import ecomhub.authservice.infrastructure.data.persistence.entity.AccountEntity;
import ecomhub.authservice.infrastructure.data.persistence.entity.AccountRoleEntity;
import ecomhub.authservice.infrastructure.data.persistence.entity.id.AccountRoleId;
import ecomhub.authservice.infrastructure.data.persistence.mapper.AccountPersistenceMapper;
import ecomhub.authservice.infrastructure.data.persistence.repository.AccountJpaRepository;
import ecomhub.authservice.infrastructure.data.persistence.specification.AccountSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class AccountRepository implements AccountRepositoryPort {
    private final AccountJpaRepository accountJpaRepository;

    @Override
    public Account save(Account account) {
        var accountEntity = accountJpaRepository.save(AccountPersistenceMapper.toEntity(account));
        Set<AccountRoleId> accountRoleIds = buildAccountRoleIds(account.getRoleIds(), accountEntity.getId());
        Set<AccountRoleEntity> accountRoleEntities = buildAccountRoles(accountRoleIds);
        accountEntity.setAccountRoles(accountRoleEntities);
        return AccountPersistenceMapper.toDomain(accountEntity);
    }

    @Override
    public Optional<Account> findByIdentifier(String identifier) {
        Optional<AccountEntity> account = accountJpaRepository.findOne(AccountSpecification.byIdentifier(identifier));
        return account.map(AccountPersistenceMapper::toDomain);
    }

    @Override
    public boolean existsByIdentifier(String identifier) {
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
