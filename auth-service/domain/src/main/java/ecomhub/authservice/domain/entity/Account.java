package ecomhub.authservice.domain.entity;

import ecomhub.authservice.common.enums.ProviderType;
import ecomhub.authservice.common.exception.concrete.account.NoRoleAssignedException;
import ecomhub.authservice.common.exception.concrete.account.RoleAlreadyAssignedException;
import ecomhub.authservice.common.exception.concrete.account.RoleNotAssignedException;
import ecomhub.authservice.common.exception.concrete.account.MissingIdInAccountException;
import ecomhub.authservice.common.exception.concrete.account.MissingRoleException;
import ecomhub.authservice.domain.service.PasswordHashService;
import ecomhub.authservice.domain.valueobject.*;

import java.util.*;


public class Account {
    private UUID id;
    private Email email;
    private Username username;
    private PhoneNumber phoneNumber;
    private Password passwordHash;
    private Provider provider;
    private boolean active;
    private Set<Role> roles;

    //Constructor lấy dữ liệu từ DB
    public Account(UUID id, String email, String username, String phoneNumber, String passwordHash, String provider, boolean active, Set<Role> roles) {
        validateForLoad(id, roles);
        this.id = id;
        this.email = new Email(email, "tài khoản");
        this.username = new Username(username);
        this.phoneNumber = new PhoneNumber(phoneNumber, "tài khoản");
        this.passwordHash = new Password(passwordHash);
        this.provider = new Provider(provider);
        this.active = active;
        this.roles = roles;
    }

    public Account(String email, String username, String phoneNumber, String rawPassword, String provider, PasswordHashService passwordHashService) {
        this.id = UUID.randomUUID();
        this.email = new Email(email, "tài khoản");
        this.username = new Username(username);
        this.phoneNumber = new PhoneNumber(phoneNumber, "tài khoản");
        this.passwordHash = new Password(rawPassword, passwordHashService);
        this.provider = provider == null ? new Provider(ProviderType.LOCAL.name()) : new Provider(provider);
        this.active = true;
        this.roles = new HashSet<>();
    }

    public UUID getId() {
        return id;
    }

    public Email getEmail() {
        return email;
    }

    public Optional<Username> getUsername() {
        return Optional.ofNullable(username);
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public Optional<Password> getPasswordHash() {
        return Optional.ofNullable(passwordHash);
    }

    public Provider getProvider() {
        return provider;
    }

    public boolean isActive() {
        return active;
    }

    public Set<Role> getRoles() {
        return Set.copyOf(roles);
    }

    public void grantRole(Role role) {
        if (role == null) {
            throw new MissingRoleException();
        }
        if (this.roles.contains(role)) {
            throw new RoleAlreadyAssignedException(role.getName().getValue());
        }
        this.roles.add(role);
    }

    public void revokeRole(Role role) {
        if (role == null) {
            throw new MissingRoleException();
        }
        if (!this.roles.contains(role)) {
            throw new RoleNotAssignedException(role.getName().getValue());
        }
        this.roles.remove(role);
    }


    private void validateForLoad(UUID id, Set<Role> roles) {
        if (id == null) {
            throw new MissingIdInAccountException();
        }
        if (roles.isEmpty()) {
            throw new NoRoleAssignedException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

