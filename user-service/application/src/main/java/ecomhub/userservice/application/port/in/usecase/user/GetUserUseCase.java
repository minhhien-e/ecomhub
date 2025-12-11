package ecomhub.userservice.application.port.in.usecase.user;

import ecomhub.userservice.application.dto.query.user.GetUserQuery;
import ecomhub.userservice.application.port.in.usecase.base.UseCase;
import ecomhub.userservice.domain.aggregate.User;

public interface GetUserUseCase extends UseCase<User, GetUserQuery> {
}
