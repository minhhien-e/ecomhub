package ecomhub.userservice.application.service.usecase.usersetting;

import ecomhub.userservice.application.dto.command.usersetting.UpdateLanguageCommand;
import ecomhub.userservice.application.port.in.usecase.usersetting.UpdateLanguageUseCase;
import ecomhub.userservice.domain.aggregate.User;
import ecomhub.userservice.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateLanguageUseCaseImpl implements UpdateLanguageUseCase {

    private final UserRepository userRepository;

    public UpdateLanguageUseCaseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Void execute(UpdateLanguageCommand command) {
        User user = userRepository.getById(command.userId());

        user.changeLanguage(command.newLanguage());
        userRepository.save(user);
        return null;
    }
}
