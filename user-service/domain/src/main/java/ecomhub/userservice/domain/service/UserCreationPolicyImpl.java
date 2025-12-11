package ecomhub.userservice.domain.service;

import ecomhub.userservice.domain.exception.user.UserAlreadyExistsException;
import ecomhub.userservice.domain.policy.UserCreationPolicy;
import ecomhub.userservice.domain.repository.UserRepository;

public class UserCreationPolicyImpl implements UserCreationPolicy {
    private final UserRepository userRepository;

    public UserCreationPolicyImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void ensureCanCreate(String username, String email) {
        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException("User with username " + username + " already exists");
        }
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("User with email " + email + " already exists");
        }
    }
}
