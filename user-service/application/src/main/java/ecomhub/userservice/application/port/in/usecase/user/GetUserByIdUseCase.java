package ecomhub.userservice.application.port.in.usecase.user;

import ecomhub.userservice.application.dto.query.user.GetUserByIdQuery;
import ecomhub.userservice.application.dto.readmodel.UserReadModel;
import ecomhub.userservice.application.port.in.usecase.base.UseCase;

public interface GetUserByIdUseCase extends UseCase<UserReadModel, GetUserByIdQuery> {
}
