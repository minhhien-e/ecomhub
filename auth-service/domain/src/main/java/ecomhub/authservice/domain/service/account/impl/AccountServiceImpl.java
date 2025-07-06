package ecomhub.authservice.domain.service.account.impl;

import ecomhub.authservice.common.exception.AccountAlreadyExistsException;
import ecomhub.authservice.domain.model.Account;
import ecomhub.authservice.domain.repository.AccountRepository;
import ecomhub.authservice.domain.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    public void registerAccount(Account account) {
        if (isAccountExists(account.getEmail())) {
            throw new AccountAlreadyExistsException(String.format("Email %s đã tồn tại", account.getEmail()));
        }
        if (isAccountExists(account.getUsername())) {
            throw new AccountAlreadyExistsException(String.format("Username %s đã tồn tại", account.getEmail()));
        }
        if (isAccountExists(account.getPhoneNumber())) {
            throw new AccountAlreadyExistsException(String.format("Số điện thoại %s đã tồn tại", account.getEmail()));
        }
        accountRepository.save(account);
    }

    @Override
    public boolean isAccountExists(String identifier) {
        return accountRepository.isAccountExists(identifier);
    }


}
