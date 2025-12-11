package ecomhub.userservice.application.service.usecase.user;

import ecomhub.userservice.application.dto.command.user.ChangePhoneNumberCommand;
import ecomhub.userservice.application.port.in.usecase.user.ChangePhoneNumberUseCase;
import ecomhub.userservice.domain.aggregate.User;
import ecomhub.userservice.domain.exception.user.UserNotFoundException;
import ecomhub.userservice.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ChangePhoneNumberUseCaseImpl implements ChangePhoneNumberUseCase {

    private final UserRepository userRepository;

    public ChangePhoneNumberUseCaseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Void execute(ChangePhoneNumberCommand command) {
        User user = userRepository.getById(command.userId());

        user.changePhoneNumber(command.newPhoneNumber());
        userRepository.save(user);
        return null;
    }
}
