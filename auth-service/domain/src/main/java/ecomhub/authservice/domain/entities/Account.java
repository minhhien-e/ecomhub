package ecomhub.authservice.domain.entities;

import ecomhub.authservice.common.enums.Provider;
import ecomhub.authservice.common.exception.ValidationException;
import ecomhub.authservice.common.helper.ValidationHelper;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private UUID id;
    private String email;
    private String username;
    private String phoneNumber;
    private String passwordHash;
    private Provider provider;
    private boolean active;
    private Set<UUID> roleIds;

    public void deActivate() {
        this.active = false;
    }

    public void activate() {
        this.active = true;
    }

    public void addRole(UUID roleId) {
        this.roleIds.add(roleId);
    }

    public void removeRole(UUID roleId) {
        this.roleIds.remove(roleId);
    }

    public boolean hasRole(UUID roleId) {
        return roleIds.contains(roleId);
    }


    /**
     * Kiểm tra tính hợp lệ của thông tin đăng ký
     */
    public void validateForRegistration() {
        ValidationHelper.requireNonBlank("username", username);
        ValidationHelper.requireNonBlank("email", email);
        ValidationHelper.requireNonBlank("phoneNumber", phoneNumber);
        ValidationHelper.requireNonBlank("passwordHash", passwordHash);
        if (provider == null || provider != Provider.LOCAL) {
            throw new ValidationException("Provider phải là LOCAL và khác null");
        }
        if(roleIds==null){
            roleIds=Set.of();
        }
    }

    /**
     * Kiểm tra tính hợp lệ của thông tin đăng nhập
     */
    public void validateForLogin() {
        boolean hasIdentifier =
                !StringUtils.isBlank(username) ||
                        !StringUtils.isBlank(email) ||
                        !StringUtils.isBlank(phoneNumber);

        if (!hasIdentifier) {
            throw new ValidationException("Phải cung cấp ít nhất 1 loại thông tin username, phoneNumber hoặc email");
        }

        ValidationHelper.requireNonBlank("password", passwordHash);
    }
}

