package ecomhub.userservice.api.mapper;


import ecomhub.userservice.api.dto.request.usersetting.GetUserSettingByUserIdRequest;
import ecomhub.userservice.api.dto.request.usersetting.UpdateDarkModeRequest;
import ecomhub.userservice.api.dto.request.usersetting.UpdateLanguageRequest;
import ecomhub.userservice.api.dto.request.usersetting.UpdateMarketingEmailRequest;
import ecomhub.userservice.application.dto.command.usersetting.UpdateDarkModeCommand;
import ecomhub.userservice.application.dto.command.usersetting.UpdateLanguageCommand;
import ecomhub.userservice.application.dto.command.usersetting.UpdateMarketingEmailCommand;
import ecomhub.userservice.application.dto.query.usersetting.GetUserSettingByUserIdQuery;

import java.util.UUID;

public final class UserSettingMapper {
    public static UpdateLanguageCommand fromRequest(UUID userId, UpdateLanguageRequest request) {
        return new UpdateLanguageCommand(userId, request.newLanguage());
    }

    public static UpdateDarkModeCommand fromRequest(UUID userId, UpdateDarkModeRequest request) {
        return new UpdateDarkModeCommand(userId, request.isDarkMode());
    }

    public static UpdateMarketingEmailCommand fromRequest(UUID userId, UpdateMarketingEmailRequest request) {
        return new UpdateMarketingEmailCommand(userId, request.receiveMarketingEmail());
    }

    //Read
    public static GetUserSettingByUserIdQuery fromRequest(UUID userId, GetUserSettingByUserIdRequest request) {
        return new GetUserSettingByUserIdQuery(userId);
    }

}
