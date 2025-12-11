package ecomhub.userservice.infrastructure.mapper;

import ecomhub.userservice.application.dto.readmodel.UserReadModel;
import ecomhub.userservice.domain.aggregate.User;
import ecomhub.userservice.infrastructure.persistence.entity.UserEntity;

public class UserMapper {
    public static UserEntity toEntity(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .username(user.getUsername().value())
                .email(user.getEmail().value())
                .password(user.getPassword())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .gender(user.getGender())
                .birthDate(user.getBirthDate())
                .avatarUrl(user.getAvatarUrl())
                .createdAt(user.getCreatedAt())
                .roles(user.getRoles())
                .isActive(user.isActive())
                .setting(UserSettingMapper.toEntity(user.getSetting()))
                .build();
    }

    public static User toDomain(UserEntity entity) {
        return User.reconstruct(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getFullName(),
                entity.getPhoneNumber(),
                entity.getGender(),
                entity.getBirthDate(),
                entity.getAvatarUrl(),
                entity.getCreatedAt(),
                entity.getRoles(),
                entity.isActive(),
                UserSettingMapper.toDomain(entity.getSetting())
        );
    }

    public static UserReadModel toReadModel(UserEntity entity) {
        return new UserReadModel(
                entity.getId(),
                entity.getFullName(),
                entity.getEmail(),
                entity.getPhoneNumber(),
                entity.getGender(),
                entity.getBirthDate(),
                entity.getAvatarUrl(),
                entity.getCreatedAt()
        );
    }
}
