package ecomhub.userservice.application.service.usecase.usersetting;

import ecomhub.userservice.application.dto.command.usersetting.UpdateMarketingEmailCommand;
import ecomhub.userservice.application.port.in.usecase.usersetting.UpdateMarketingEmailUseCase;
import ecomhub.userservice.domain.aggregate.User;
import ecomhub.userservice.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateMarketingEmailUseCaseImpl implements UpdateMarketingEmailUseCase {

    private final UserRepository userRepository;

    public UpdateMarketingEmailUseCaseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Void execute(UpdateMarketingEmailCommand command) {
        User user = userRepository.getById(command.userId());

        user.changeMarketingEmail(command.receiveMarketingEmail());
        userRepository.save(user);
        return null;
    }
}
