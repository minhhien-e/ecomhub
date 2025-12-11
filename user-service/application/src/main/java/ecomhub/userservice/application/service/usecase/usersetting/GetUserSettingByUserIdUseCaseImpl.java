package ecomhub.userservice.application.service.usecase.usersetting;

import ecomhub.userservice.application.dto.query.usersetting.GetUserSettingByUserIdQuery;
import ecomhub.userservice.application.dto.readmodel.UserSettingReadModel;
import ecomhub.userservice.application.port.in.usecase.usersetting.GetUserSettingByUserIdUseCase;
import ecomhub.userservice.application.port.out.repository.UserSettingReadRepository;
import ecomhub.userservice.domain.exception.user.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GetUserSettingByUserIdUseCaseImpl implements GetUserSettingByUserIdUseCase {

    private final UserSettingReadRepository userSettingReadRepository;

    public GetUserSettingByUserIdUseCaseImpl(UserSettingReadRepository userSettingReadRepository) {
        this.userSettingReadRepository = userSettingReadRepository;
    }

    @Override
    public UserSettingReadModel execute(GetUserSettingByUserIdQuery query) {
        return userSettingReadRepository.getByUserId(query.userId());
    }
}
