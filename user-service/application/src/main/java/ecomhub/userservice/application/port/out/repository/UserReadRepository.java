package ecomhub.userservice.application.port.out.repository;

import ecomhub.userservice.application.dto.readmodel.UserReadModel;

import java.util.UUID;

public interface UserReadRepository {
    UserReadModel getById(UUID id);
}
