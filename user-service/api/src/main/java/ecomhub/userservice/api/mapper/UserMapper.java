package ecomhub.userservice.api.mapper;


import ecomhub.userservice.api.dto.request.user.*;
import ecomhub.userservice.api.dto.response.user.UserResponse;
import ecomhub.userservice.application.dto.command.user.*;
import ecomhub.userservice.application.dto.query.user.GetUserByIdQuery;
import ecomhub.userservice.application.dto.readmodel.UserReadModel;

import java.util.UUID;

/**
 * Mapper responsible for converting web layer Request DTOs into Application layer Command objects.
 * This is a crucial boundary mapping to keep the application core independent of web details.
 */
public final class UserMapper {

    public static CreateUserCommand fromRequest(CreateUserRequest request) {
        return new CreateUserCommand(request.fullName(), request.email(), request.phoneNumber());
    }

    public static RenameUserCommand fromRequest(UUID userId, RenameUserRequest request) {
        return new RenameUserCommand(userId, request.newName());
    }

    public static ChangeEmailCommand fromRequest(UUID userId, ChangeEmailRequest request) {
        return new ChangeEmailCommand(userId, request.newEmail());
    }

    public static ChangePhoneNumberCommand fromRequest(UUID userId, ChangePhoneNumberRequest request) {
        return new ChangePhoneNumberCommand(userId, request.newPhoneNumber());
    }

    public static ChangeAvatarCommand fromRequest(UUID userId, ChangeAvatarRequest request) {
        return new ChangeAvatarCommand(userId, request.newAvatarUrl());
    }

    public static ChangeGenderCommand fromRequest(UUID userId, ChangeGenderRequest request) {
        return new ChangeGenderCommand(userId, request.newGender());
    }

    public static ChangeBirthDateCommand fromRequest(UUID userId, ChangeBirthDateRequest request) {
        return new ChangeBirthDateCommand(userId, request.newBirthDate());
    }

    // Read
    public static GetUserByIdQuery fromRequest(UUID userId, GetUserByIdRequest request) {
        return new GetUserByIdQuery(userId);
    }

    public static UserResponse fromReadModel(UserReadModel readModel) {
        return new UserResponse(
                readModel.id(),
                readModel.fullName(),
                readModel.email(),
                readModel.phoneNumber(),
                readModel.gender(),
                readModel.birthDate(),
                readModel.avatarUrl(),
                readModel.createdAt()
        );
    }
}
