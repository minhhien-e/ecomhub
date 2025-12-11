package ecomhub.userservice.application.service.usecase.user;

import ecomhub.userservice.application.dto.command.user.ChangeEmailCommand;
import ecomhub.userservice.application.port.in.usecase.user.ChangeEmailUseCase;
import ecomhub.userservice.domain.aggregate.User;
import ecomhub.userservice.domain.exception.user.UserNotFoundException;
import ecomhub.userservice.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ChangeEmailUseCaseImpl implements ChangeEmailUseCase {

    private final UserRepository userRepository;

    public ChangeEmailUseCaseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Void execute(ChangeEmailCommand command) {
        User user = userRepository.getById(command.userId());

        user.changeEmail(command.newEmail());
        userRepository.save(user);
        return null;
    }
}
