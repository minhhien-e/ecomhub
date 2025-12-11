package ecomhub.userservice.domain.entity;

import io.github.domain.aggregate.base.AggregateChild;

import java.time.LocalDateTime;

public class UserSetting  extends AggregateChild {
    private String language;
    private boolean darkMode;
    private boolean receiveMarketingEmail;
    private LocalDateTime updatedAt;

    public UserSetting() {
    }

    public void updateLanguage(String language) {
        this.language = language;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateDarkMode(boolean darkMode) {
        this.darkMode = darkMode;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateMarketingEmail(boolean receiveMarketingEmail) {
        this.receiveMarketingEmail = receiveMarketingEmail;
        this.updatedAt = LocalDateTime.now();
    }

    public static UserSetting create() {
        UserSetting setting = new UserSetting();
        setting.language = "vi";
        setting.darkMode = false;
        setting.receiveMarketingEmail = true;
        setting.updatedAt = LocalDateTime.now();
        return setting;
    }

    public static UserSetting reconstruct(String language, boolean darkMode, boolean receiveMarketingEmail, LocalDateTime updatedAt) {
        UserSetting setting = new UserSetting();
        setting.language = language;
        setting.darkMode = darkMode;
        setting.receiveMarketingEmail = receiveMarketingEmail;
        setting.updatedAt = updatedAt;
        return setting;
    }

    public String getLanguage() {
        return language;
    }

    public boolean isDarkMode() {
        return darkMode;
    }

    public boolean isReceiveMarketingEmail() {
        return receiveMarketingEmail;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
