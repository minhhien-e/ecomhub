package ecomhub.userservice.api.mapper;

import ecomhub.userservice.api.dto.request.user.*;
import ecomhub.userservice.application.dto.command.user.*;
import ecomhub.userservice.application.dto.query.user.GetUserByIdQuery;

import java.util.UUID;

public class UserMapper {
    /// Create
    public static CreateUserCommand toCommand(UUID userId, CreateUserRequest request) {
        return new CreateUserCommand(
                userId,
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                request.getRoles()
        );
    }

    /// Read
    public static GetUserByIdQuery toQuery(UUID userId, GetUserByIdRequest request) {
        return new GetUserByIdQuery(userId);
    }

    /// Update
    public static ChangeAvatarCommand toCommand(UUID userId, ChangeAvatarRequest request) {
        return new ChangeAvatarCommand(userId, request.newAvatarUrl());
    }

    public static ChangeBirthDateCommand toCommand(UUID userId, ChangeBirthDateRequest request) {
        return new ChangeBirthDateCommand(userId, request.newBirthDate());
    }

    public static ChangeEmailCommand toCommand(UUID userId, ChangeEmailRequest request) {
        return new ChangeEmailCommand(userId, request.newEmail());
    }

    public static ChangeGenderCommand toCommand(UUID userId, ChangeGenderRequest request) {
        return new ChangeGenderCommand(userId, request.newGender());
    }

    public static ChangePhoneNumberCommand toCommand(UUID userId, ChangePhoneNumberRequest request) {
        return new ChangePhoneNumberCommand(userId, request.newPhoneNumber());
    }

    public static RenameUserCommand toCommand(UUID userId, RenameUserRequest request) {
        return new RenameUserCommand(userId, request.newName());
    }

    /// Delete
    public static DeleteUserCommand toCommand(UUID userId, DeleteUserRequest request) {
        return new DeleteUserCommand(userId);
    }


}
