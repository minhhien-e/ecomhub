package ecomhub.userservice.application.service.usecase.user;

import ecomhub.userservice.application.dto.command.user.CreateUserCommand;
import ecomhub.userservice.application.port.in.usecase.user.CreateUserUseCase;
import ecomhub.userservice.domain.aggregate.User;
import ecomhub.userservice.domain.policy.UserCreationPolicy;
import ecomhub.userservice.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCaseImpl implements CreateUserUseCase {

    private final UserRepository userRepository;
    private final UserCreationPolicy userCreationPolicy;

    public CreateUserUseCaseImpl(UserRepository userRepository, UserCreationPolicy userCreationPolicy) {
        this.userRepository = userRepository;
        this.userCreationPolicy = userCreationPolicy;
    }

    @Override
    public Void execute(CreateUserCommand command) {
        userCreationPolicy.ensureCanCreate(command.username(), command.email());

        User user = User.create(
                command.username(),
                command.email(),
                command.password(),
                command.roles()
        );

        userRepository.save(user);

        return null;
    }
}
