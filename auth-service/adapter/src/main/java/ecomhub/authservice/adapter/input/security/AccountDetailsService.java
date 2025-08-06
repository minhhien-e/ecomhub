package ecomhub.authservice.adapter.input.security;

import ecomhub.authservice.application.port.security.LoadAccountByIdentifierPort;
import ecomhub.authservice.common.enums.ProviderType;
import ecomhub.authservice.common.exception.concrete.valueobject.password.MissingPasswordException;
import ecomhub.authservice.domain.entity.Account;
import ecomhub.authservice.domain.valueobject.Provider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
                        () ->   new UsernameNotFoundException("Tài khoản hoặc mật khẩu không đúng")
                );
        if (!account.getProvider().isSame(new Provider(ProviderType.LOCAL.name()))) {
            throw new UsernameNotFoundException("Tài khoản hoặc mật khẩu không đúng");
        }
        if (account.getPasswordHash().isEmpty())
            throw new MissingPasswordException();

        return User.builder()
                .username(account.getEmail().getValue())
                .password(account.getPasswordHash().get().getHashedValue())
                .roles(account.getRoles()
                        .stream().map(role -> role.getName().getValue())
                        .distinct().toArray(String[]::new))
                .build();
    }

}
