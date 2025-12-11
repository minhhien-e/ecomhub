package ecomhub.userservice.application.service.usecase.user;

import ecomhub.userservice.application.dto.query.user.GetUserByIdQuery;
import ecomhub.userservice.application.dto.readmodel.UserReadModel;
import ecomhub.userservice.application.port.in.usecase.user.GetUserByIdUseCase;
import ecomhub.userservice.application.port.out.repository.UserReadRepository;
import ecomhub.userservice.domain.exception.user.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GetUserByIdUseCaseImpl implements GetUserByIdUseCase {

    private final UserReadRepository userReadRepository;

    public GetUserByIdUseCaseImpl(UserReadRepository userReadRepository) {
        this.userReadRepository = userReadRepository;
    }

    @Override
    public UserReadModel execute(GetUserByIdQuery query) {
        return userReadRepository.getById(query.userId());
    }
}
