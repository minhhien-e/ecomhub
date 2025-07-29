package ecomhub.authservice.adapter.input.security;

import ecomhub.authservice.application.port.repository.RoleRepositoryPort;
import ecomhub.authservice.application.port.security.LoadAccountByIdentifierPort;
import ecomhub.authservice.common.enums.ProviderType;
import ecomhub.authservice.common.exception.concrete.role.RoleNotFoundException;
import ecomhub.authservice.domain.entity.Account;
import ecomhub.authservice.domain.entity.Role;
import ecomhub.authservice.domain.valueobject.Provider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountDetailsService implements UserDetailsService {
    private final LoadAccountByIdentifierPort loadAccountByIdentifier;
    private final RoleRepositoryPort roleRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        Account account = loadAccountByIdentifier
                .loadByIdentifier(identifier)
                .orElseThrow(
                        () -> new UsernameNotFoundException(identifier)
                );
        if (!account.getProvider().isSame(new Provider(ProviderType.LOCAL.name()))) {
            throw new UsernameNotFoundException(identifier);
        }

        return User.builder()
                .username(account.getEmail().getValue())
                .roles(account.getRoles()
                        .stream().map(role -> role.getName().getValue())
                        .distinct().toArray(String[]::new))
                .build();
    }

}
