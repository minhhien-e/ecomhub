package ecomhub.userservice.application.service.usecase.user;

import ecomhub.userservice.application.dto.command.user.ChangeGenderCommand;
import ecomhub.userservice.application.port.in.usecase.user.ChangeGenderUseCase;
import ecomhub.userservice.domain.aggregate.User;
import ecomhub.userservice.domain.exception.user.UserNotFoundException;
import ecomhub.userservice.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ChangeGenderUseCaseImpl implements ChangeGenderUseCase {

    private final UserRepository userRepository;

    public ChangeGenderUseCaseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Void execute(ChangeGenderCommand command) {
        User user = userRepository.getById(command.userId());

        user.changeGender(command.newGender());
        userRepository.save(user);
        return null;
    }
}
