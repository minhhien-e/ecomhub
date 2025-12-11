package ecomhub.userservice.api.web.rest;

import ecomhub.userservice.api.dto.request.user.*;
import ecomhub.userservice.api.helper.RequestExecutor;
import ecomhub.userservice.api.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final RequestExecutor requestExecutor;

    /// Create
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest request) {
        return requestExecutor.executeWithCurrentUser(UserMapper::toCommand, request, HttpStatus.CREATED);
    }

    /// Read
    @GetMapping
    public ResponseEntity<?> getUserById() {
        var request = new GetUserByIdRequest();
        return requestExecutor.executeWithCurrentUser(UserMapper::toQuery, request, HttpStatus.OK);
    }

    /// Update
    @PatchMapping("/avatar")
    public ResponseEntity<?> changeAvatar(@RequestBody ChangeAvatarRequest request) {
        return requestExecutor.executeWithCurrentUser(UserMapper::toCommand, request, HttpStatus.OK);
    }

    @PatchMapping("/birth-date")
    public ResponseEntity<?> changeBirthDate(@RequestBody ChangeBirthDateRequest request) {
        return requestExecutor.executeWithCurrentUser(UserMapper::toCommand, request, HttpStatus.OK);
    }

    @PatchMapping("/email")
    public ResponseEntity<?> changeEmail(@RequestBody ChangeEmailRequest request) {
        return requestExecutor.executeWithCurrentUser(UserMapper::toCommand, request, HttpStatus.OK);
    }

    @PatchMapping("/gender")
    public ResponseEntity<?> changeGender(@RequestBody ChangeGenderRequest request) {
        return requestExecutor.executeWithCurrentUser(UserMapper::toCommand, request, HttpStatus.OK);
    }

    @PatchMapping("/phone-number")
    public ResponseEntity<?> changePhoneNumber(@RequestBody ChangePhoneNumberRequest request) {
        return requestExecutor.executeWithCurrentUser(UserMapper::toCommand, request, HttpStatus.OK);
    }

    @PatchMapping("/rename")
    public ResponseEntity<?> renameUser(@RequestBody RenameUserRequest request) {
        return requestExecutor.executeWithCurrentUser(UserMapper::toCommand, request, HttpStatus.OK);
    }

    /// Delete
    @DeleteMapping
    public ResponseEntity<?> deleteUser() {
        var request = new DeleteUserRequest();
        return requestExecutor.executeWithCurrentUser(UserMapper::toCommand, request, HttpStatus.OK);
    }


}
