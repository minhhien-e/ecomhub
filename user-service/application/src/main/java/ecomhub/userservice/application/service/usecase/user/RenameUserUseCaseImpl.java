package ecomhub.userservice.application.service.usecase.user;

import ecomhub.userservice.application.dto.command.user.RenameUserCommand;
import ecomhub.userservice.application.port.in.usecase.user.RenameUserUseCase;
import ecomhub.userservice.domain.aggregate.User;
import ecomhub.userservice.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class RenameUserUseCaseImpl implements RenameUserUseCase {

    private final UserRepository userRepository;

    public RenameUserUseCaseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Void execute(RenameUserCommand command) {
        User user = userRepository.getById(command.userId());

        user.updateFullName(command.newName());
        userRepository.save(user);
        return null;
    }
}
