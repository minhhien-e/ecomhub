package ecomhub.userservice.application.usecase.handler.user;

import ecomhub.userservice.application.dto.query.user.GetUserByIdQuery;
import ecomhub.userservice.application.dto.readmodel.UserReadModel;
import ecomhub.userservice.application.port.in.usecase.user.GetUserByIdUseCase;
import org.springframework.stereotype.Service;

@Service
public class GetUserByIdUseCaseImpl implements GetUserByIdUseCase {
    @Override
    public UserReadModel execute(GetUserByIdQuery params) {
        return null;
    }
}
