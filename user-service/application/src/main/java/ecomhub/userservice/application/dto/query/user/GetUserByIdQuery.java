package ecomhub.userservice.application.dto.query.user;

import ecomhub.userservice.application.dto.base.Request;
import ecomhub.userservice.application.dto.readmodel.UserReadModel;

import java.util.UUID;

public record GetUserByIdQuery(UUID userId) implements Request<UserReadModel> {
}
