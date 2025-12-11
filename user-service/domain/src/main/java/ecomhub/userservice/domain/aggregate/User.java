package ecomhub.userservice.domain.aggregate;

import io.github.domain.aggregate.base.AggregateRoot;
import ecomhub.userservice.domain.entity.UserSetting;
import ecomhub.userservice.domain.vo.Email;
import ecomhub.userservice.domain.vo.Username;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User extends AggregateRoot {
    private Username username;
    private Email email;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String gender;
    private LocalDate birthDate;
    private String avatarUrl;
    private LocalDateTime createdAt;
    private List<String> roles;
    private boolean isActive;
    private UserSetting setting;

    public User(UUID id) {
        super(id);
    }

    public User() {
    }

    // Business Logic
    public void updatePassword(String newPassword) {
        this.password = newPassword;
        markChanged("password");
    }

    public void updateRoles(List<String> newRoles) {
        this.roles = new ArrayList<>(newRoles);
        markChanged("roles");
    }

    public void deactivate() {
        this.isActive = false;
        markChanged("isActive");
    }

    public void activate() {
        this.isActive = true;
        markChanged("isActive");
    }

    public void updateFullName(String fullName) {
        this.fullName = fullName;
        markChanged("fullName");
    }
    
    public void changeEmail(String newEmail) {
        this.email = new Email(newEmail);
        markChanged("email");
    }

    public void changePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        markChanged("phoneNumber");
    }

    public void changeGender(String gender) {
        this.gender = gender;
        markChanged("gender");
    }

    public void changeBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        markChanged("birthDate");
    }

    public void changeAvatar(String avatarUrl) {
        this.avatarUrl = avatarUrl;
        markChanged("avatarUrl");
    }

    public void changeLanguage(String language) {
        if (this.setting == null) {
            this.setting = UserSetting.create();
        }
        this.setting.updateLanguage(language);
        markNestedChanged(this.setting, "setting");
    }

    public void changeDarkMode(boolean darkMode) {
        if (this.setting == null) {
            this.setting = UserSetting.create();
        }
        this.setting.updateDarkMode(darkMode);
        markNestedChanged(this.setting, "setting");
    }

    public void changeMarketingEmail(boolean receiveMarketingEmail) {
        if (this.setting == null) {
            this.setting = UserSetting.create();
        }
        this.setting.updateMarketingEmail(receiveMarketingEmail);
        markNestedChanged(this.setting, "setting");
    }

    // Factory method
    public static User create(String username, String email, String password, List<String> roles) {
        User user = new User();
        user.username = new Username(username);
        user.email = new Email(email);
        user.password = password;
        user.roles = new ArrayList<>(roles);
        user.isActive = true;
        user.createdAt = LocalDateTime.now();
        user.setting = UserSetting.create();
        return user;
    }

    public static User reconstruct(UUID id, String username, String email, String password, 
                                   String fullName, String phoneNumber, String gender, LocalDate birthDate, String avatarUrl, LocalDateTime createdAt,
                                   List<String> roles, boolean isActive, UserSetting setting) {
        User user = new User(id);
        user.username = new Username(username);
        user.email = new Email(email);
        user.password = password;
        user.fullName = fullName;
        user.phoneNumber = phoneNumber;
        user.gender = gender;
        user.birthDate = birthDate;
        user.avatarUrl = avatarUrl;
        user.createdAt = createdAt;
        user.roles = new ArrayList<>(roles);
        user.isActive = isActive;
        user.setting = setting;
        return user;
    }

    // Getters
    public Username getUsername() {
        return username;
    }

    public Email getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<String> getRoles() {
        return List.copyOf(roles);
    }

    public boolean isActive() {
        return isActive;
    }

    public UserSetting getSetting() {
        return setting;
    }
}
