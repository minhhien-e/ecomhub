package ecomhub.authservice.domain.entity;

import ecomhub.authservice.common.enums.Provider;
import ecomhub.authservice.common.exception.concrete.account.business.NoRoleAssignedException;
import ecomhub.authservice.common.exception.concrete.account.conflict.RoleAlreadyAssignedException;
import ecomhub.authservice.common.exception.concrete.account.notfound.RoleNotAssignedException;
import ecomhub.authservice.common.exception.concrete.account.validation.*;
import ecomhub.authservice.common.exception.concrete.role.validation.RoleIdRequiredException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;


public class Account {
    private UUID id;
    private String email;
    private String username;
    private String phoneNumber;
    private String passwordHash;
    private Provider provider;
    private boolean active;
    private Set<UUID> roleIds;

    //Constructor lấy dữ liệu từ DB
    public Account(UUID id, String email, String username, String phoneNumber, String passwordHash, Provider provider, boolean active, Set<UUID> roleIds) {
        validateForLoad(id, roleIds, email, username, phoneNumber, passwordHash, provider);
        this.id = id;
        this.email = email;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.passwordHash = passwordHash;
        this.provider = provider;
        this.active = active;
        this.roleIds = roleIds;
    }

    public Account(String email, String username, String phoneNumber, String passwordHash, Provider provider) {
        validateForCreate(email, username, phoneNumber, passwordHash, provider);
        this.id = UUID.randomUUID();
        this.email = email;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.passwordHash = passwordHash;
        this.provider = provider == null ? Provider.LOCAL : provider;
        this.active = true;
        this.roleIds = new HashSet<>();
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Optional<String> getUsername() {
        return Optional.ofNullable(username);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Optional<String> getPasswordHash() {
        return Optional.ofNullable(passwordHash);
    }

    public Provider getProvider() {
        return provider;
    }

    public boolean isActive() {
        return active;
    }

    public Set<UUID> getRoleIds() {
        return Set.copyOf(roleIds);
    }

    public void grantRole(UUID roleId) {
        if (roleId == null) {
            throw new RoleIdRequiredException();
        }
        if (this.roleIds.contains(roleId)) {
            throw new RoleAlreadyAssignedException(roleId);
        }
        this.roleIds.add(roleId);
    }

    public void revokeRole(UUID roleId) {
        if (roleId == null) {
            throw new RoleIdRequiredException();
        }
        if (!this.roleIds.contains(roleId)) {
            throw new RoleNotAssignedException(roleId);
        }
        this.roleIds.remove(roleId);
    }

    /**
     * Kiểm tra tính hợp lệ của thông tin khi tạo
     */
    private void validateForCreate(String email, String username, String phoneNumber, String passwordHash, Provider provider) {
        if (email == null || email.isBlank()) {
            throw new EmailRequiredException();
        }
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new PhoneNumberRequiredException();
        }
        if (provider == Provider.LOCAL) {
            if (username == null || username.isBlank()) {
                throw new UsernameRequiredException();
            }
            if (passwordHash == null || passwordHash.isBlank()) {
                throw new PasswordRequiredException();
            }
        }
        if (passwordHash == null || passwordHash.isBlank()) {
            throw new PasswordRequiredException();
        }
    }

    private void validateForLoad(UUID id, Set<UUID> roleIds, String email, String username, String phoneNumber, String passwordHash, Provider provider) {
        validateForCreate(email, username, phoneNumber, passwordHash, provider);
        if (id == null) {
            throw new AccountIdRequiredException();
        }
        if (roleIds.isEmpty()) {
            throw new NoRoleAssignedException();
        }
    }

}

