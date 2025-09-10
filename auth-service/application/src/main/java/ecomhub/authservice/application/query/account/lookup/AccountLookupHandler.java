package ecomhub.authservice.application.query.account.lookup;

import ecomhub.authservice.application.query.abstracts.IQueryHandler;
import ecomhub.authservice.domain.repository.AccountRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountLookupHandler implements IQueryHandler<AccountLookupQuery, Boolean> {
    private final AccountRepositoryPort accountRepository;
    @Override
    public Boolean handle(AccountLookupQuery query) {
        return accountRepository.existsByIdentifier(query.getIdentifier());
    }
}
