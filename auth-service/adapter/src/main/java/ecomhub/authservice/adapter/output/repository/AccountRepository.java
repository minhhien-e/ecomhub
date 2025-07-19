package ecomhub.authservice.adapter.output.repository;

import ecomhub.authservice.application.port.repository.AccountRepositoryPort;
import ecomhub.authservice.domain.entity.Account;
import ecomhub.authservice.infrastructure.data.persistence.entity.AccountEntity;
import ecomhub.authservice.infrastructure.data.persistence.mapper.AccountPersistenceMapper;
import ecomhub.authservice.infrastructure.data.persistence.repository.AccountJpaRepository;
import ecomhub.authservice.infrastructure.data.persistence.specification.AccountSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AccountRepository implements AccountRepositoryPort {
    private final AccountJpaRepository accountJpaRepository;

    @Override
    public void save(Account account) {
        AccountEntity accountEntity = AccountPersistenceMapper.toEntity(account);
        accountJpaRepository.save(accountEntity);
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
}
