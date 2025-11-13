package ecomhub.userservice.api.web.rest;

import ecomhub.userservice.api.dto.request.user.*;
import ecomhub.userservice.api.dto.response.ApiResponse;
import ecomhub.userservice.api.helper.CommandExecutor;
import ecomhub.userservice.api.helper.QueryExecutor;
import ecomhub.userservice.api.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final CommandExecutor commandExecutor;
    private final QueryExecutor queryExecutor;

    // === Create ===
    @PostMapping("/")
    public ResponseEntity<ApiResponse<?>> createUser(@RequestBody CreateUserRequest request) {
        return commandExecutor.sendCommand(UserMapper::fromRequest, request, HttpStatus.CREATED);
    }

    // === Read ===
    @GetMapping("/")
    public ResponseEntity<ApiResponse<?>> getById() {
        return queryExecutor.sendQueryWithCurrentUser(UserMapper::fromRequest, new GetUserByIdRequest(), HttpStatus.OK);
    }

    // === Update ===
    @PatchMapping("/full-name")
    public ResponseEntity<ApiResponse<?>> rename(@RequestBody RenameUserRequest request) {
        return commandExecutor.sendCommandWithCurrentUser(UserMapper::fromRequest, request, HttpStatus.OK);
    }

    @PatchMapping("/email")
    public ResponseEntity<ApiResponse<?>> changeEmail(@RequestBody ChangeEmailRequest request) {
        return commandExecutor.sendCommandWithCurrentUser(UserMapper::fromRequest, request, HttpStatus.OK);

    }

    @PatchMapping("/avatar")
    public ResponseEntity<ApiResponse<?>> changeAvatarUrl(@RequestBody ChangeAvatarRequest request) {
        return commandExecutor.sendCommandWithCurrentUser(UserMapper::fromRequest, request, HttpStatus.OK);

    }

    @PatchMapping("/gender")
    public ResponseEntity<ApiResponse<?>> changeGender(@RequestBody ChangeGenderRequest request) {
        return commandExecutor.sendCommandWithCurrentUser(UserMapper::fromRequest, request, HttpStatus.OK);
    }

    @PatchMapping("/birthdate")
    public ResponseEntity<ApiResponse<?>> changeBirthDate(@RequestBody ChangeBirthDateRequest request) {
        return commandExecutor.sendCommandWithCurrentUser(UserMapper::fromRequest, request, HttpStatus.OK);

    }

    @PatchMapping("/phone")
    public ResponseEntity<ApiResponse<?>> changePhoneNumber(@RequestBody ChangePhoneNumberRequest request) {
        return commandExecutor.sendCommandWithCurrentUser(UserMapper::fromRequest, request, HttpStatus.OK);
    }
}
