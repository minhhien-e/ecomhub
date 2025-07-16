package ecomhub.authservice.adapter.input.security;

import ecomhub.authservice.application.port.repository.RoleRepositoryPort;
import ecomhub.authservice.application.port.security.LoadAccountByIdentifierPort;
import ecomhub.authservice.common.enums.Provider;
import ecomhub.authservice.common.exception.concrete.role.notfound.RoleNotFoundException;
import ecomhub.authservice.domain.entity.Account;
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
        if (!account.getProvider().equals(Provider.LOCAL)) {
            throw new UsernameNotFoundException(identifier);
        }
        Set<String> rolesName = convertRoleIdToNames(account.getRoleIds());

        return User.builder()
                .username(account.getEmail())
                .password(account.getPasswordHash().orElse(""))
                .roles(rolesName.toArray(new String[0]))
                .build();
    }

    /**
     * Chuyển roleId thành roleName
     */
    private Set<String> convertRoleIdToNames(Set<UUID> roles) {
        return roles.stream()
                .map(id ->
                        roleRepository.findById(id)
                                .orElseThrow(
                                        () -> new RoleNotFoundException(id))
                                .getName())
                .collect(Collectors.toSet());
    }
}
