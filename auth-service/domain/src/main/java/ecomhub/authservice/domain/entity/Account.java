package ecomhub.authservice.domain.entity;

import ecomhub.authservice.common.exception.concrete.account.*;
import ecomhub.authservice.domain.valueobject.Email;
import ecomhub.authservice.domain.valueobject.Password;
import ecomhub.authservice.domain.valueobject.PhoneNumber;
import ecomhub.authservice.domain.valueobject.Provider;
import ecomhub.authservice.domain.valueobject.name.Username;

import java.util.*;


public class Account {
    private final UUID id;
    private Email email;
    private Username username;
    private PhoneNumber phoneNumber;
    private Password passwordHash;
    private final Provider provider;
    private boolean active;
    private final Set<Role> roles;

    public Account(UUID id, String email, String username, String phoneNumber, String passwordHash, String provider, boolean active, Set<Role> roles) {
        if (roles.isEmpty()) {
            throw new NoRoleAssignedException();
        }
        this.id = id;
        this.email = new Email(email);
        this.username = new Username(username);
        this.phoneNumber = new PhoneNumber(phoneNumber);
        this.passwordHash = new Password(passwordHash);
        this.provider = new Provider(provider);
        this.active = active;
        this.roles = new HashSet<>(roles);
    }

    public Account(String email, String username, String phoneNumber, String passwordHash, String provider) {
        this.id = UUID.randomUUID();
        this.email = new Email(email);
        this.username = new Username(username);
        this.phoneNumber = new PhoneNumber(phoneNumber);
        this.passwordHash = new Password(passwordHash);
        this.provider = new Provider(provider);
        this.active = true;
        this.roles = new HashSet<>();
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

    public Role getHighestRole() {
        return roles.stream()
                .max(Comparator.comparingInt(r -> r.getLevel().getValue()))
                .orElse(null);
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

    public void updateUsername(String newUsername) {
        this.username = new Username(newUsername);
    }

    public void updateEmail(String newEmail) {
        this.email = new Email(newEmail);
    }

    public void updatePhoneNumber(String newPhoneNumber) {
        this.phoneNumber = new PhoneNumber(newPhoneNumber);
    }

    public void updatePassword(String newPassword) {
        this.passwordHash = new Password(newPassword);
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
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

