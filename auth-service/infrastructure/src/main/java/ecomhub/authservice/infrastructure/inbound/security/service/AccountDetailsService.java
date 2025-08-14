package ecomhub.authservice.infrastructure.inbound.security.service;

import ecomhub.authservice.application.port.security.LoadAccountByIdentifierPort;
import ecomhub.authservice.common.enums.ProviderType;
import ecomhub.authservice.common.exception.concrete.valueobject.password.MissingPasswordException;
import ecomhub.authservice.domain.entity.Account;
import ecomhub.authservice.domain.entity.Permission;
import ecomhub.authservice.domain.entity.Role;
import ecomhub.authservice.domain.valueobject.Provider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AccountDetailsService implements UserDetailsService {
    private final LoadAccountByIdentifierPort loadAccountByIdentifier;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        Account account = loadAccountByIdentifier
                .loadByIdentifier(identifier)
                .orElseThrow(
                        () -> new UsernameNotFoundException("Tài khoản hoặc mật khẩu không đúng")
                );
        if (!account.getProvider().isSame(new Provider(ProviderType.LOCAL.name()))) {
            throw new UsernameNotFoundException("Tài khoản hoặc mật khẩu không đúng");
        }
        if (account.getPasswordHash().isEmpty())
            throw new MissingPasswordException();

        return User.builder()
                .username(account.getId().toString())
                .password(account.getPasswordHash().get().getHashedValue())
                .roles(account.getRoles()
                        .stream().map(Role::getPermissions)
                        .flatMap(Collection::stream)
                        .map(permission -> permission.getKey().getValue())
                        .distinct()
                        .toArray(String[]::new))
                .build();
    }

}
