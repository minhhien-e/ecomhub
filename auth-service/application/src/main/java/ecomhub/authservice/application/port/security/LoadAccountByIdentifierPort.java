package ecomhub.authservice.application.port.security;

import ecomhub.authservice.domain.entity.Account;

import java.util.Optional;

public interface LoadAccountByIdentifierPort {
    Optional<Account> loadByIdentifier(String identifier);
}
