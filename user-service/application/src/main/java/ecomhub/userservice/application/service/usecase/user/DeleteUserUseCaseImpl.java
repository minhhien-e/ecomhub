package ecomhub.userservice.application.service.usecase.user;

import ecomhub.userservice.application.dto.command.user.DeleteUserCommand;
import ecomhub.userservice.application.port.in.usecase.user.DeleteUserUseCase;
import ecomhub.userservice.domain.aggregate.User;
import ecomhub.userservice.domain.exception.user.UserNotFoundException;
import ecomhub.userservice.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteUserUseCaseImpl implements DeleteUserUseCase {

    private final UserRepository userRepository;

    public DeleteUserUseCaseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Void execute(DeleteUserCommand command) {
        User user = userRepository.getById(command.userId());

        user.deactivate();
        userRepository.save(user);
        return null;
    }
}
