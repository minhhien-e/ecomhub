package ecomhub.userservice.api.web.rest;

import ecomhub.userservice.api.dto.request.usersetting.GetUserSettingByUserIdRequest;
import ecomhub.userservice.api.dto.request.usersetting.UpdateDarkModeRequest;
import ecomhub.userservice.api.dto.request.usersetting.UpdateLanguageRequest;
import ecomhub.userservice.api.dto.request.usersetting.UpdateMarketingEmailRequest;
import ecomhub.userservice.api.dto.response.ApiResponse;
import ecomhub.userservice.api.helper.CommandExecutor;
import ecomhub.userservice.api.helper.QueryExecutor;
import ecomhub.userservice.api.mapper.UserSettingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/settings")
public class UserSettingController {
    private final CommandExecutor commandExecutor;
    private final QueryExecutor queryExecutor;

    // === Read ===
    @GetMapping("/")
    public ResponseEntity<ApiResponse<?>> getByUserId() {
        return queryExecutor.sendQueryWithCurrentUser(UserSettingMapper::fromRequest, new GetUserSettingByUserIdRequest(), HttpStatus.OK);
    }

    // === Update ===
    @PatchMapping("/language")
    public ResponseEntity<ApiResponse<?>> updateLanguage(@RequestBody UpdateLanguageRequest request) {
        return commandExecutor.sendCommandWithCurrentUser(UserSettingMapper::fromRequest, request, HttpStatus.OK);

    }

    @PatchMapping("/dark-mode")
    public ResponseEntity<ApiResponse<?>> updateDarkMode(@RequestBody UpdateDarkModeRequest request) {
        return commandExecutor.sendCommandWithCurrentUser(UserSettingMapper::fromRequest, request, HttpStatus.OK);

    }

    @PatchMapping("/marketing-email")
    public ResponseEntity<ApiResponse<?>> updateMarketingEmail(@RequestBody UpdateMarketingEmailRequest request) {
        return commandExecutor.sendCommandWithCurrentUser(UserSettingMapper::fromRequest, request, HttpStatus.OK);
    }
}
