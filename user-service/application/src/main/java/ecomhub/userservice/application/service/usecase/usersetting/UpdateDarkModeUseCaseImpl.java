package ecomhub.userservice.application.service.usecase.usersetting;

import ecomhub.userservice.application.dto.command.usersetting.UpdateDarkModeCommand;
import ecomhub.userservice.application.port.in.usecase.usersetting.UpdateDarkModeUseCase;
import ecomhub.userservice.domain.aggregate.User;
import ecomhub.userservice.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateDarkModeUseCaseImpl implements UpdateDarkModeUseCase {

    private final UserRepository userRepository;

    public UpdateDarkModeUseCaseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Void execute(UpdateDarkModeCommand command) {
        User user = userRepository.getById(command.userId());

        user.changeDarkMode(command.isDarkMode());
        userRepository.save(user);
        return null;
    }
}
