package ecomhub.userservice.domain.repository;

import io.github.domain.repository.DomainEntityRepository;
import ecomhub.userservice.domain.aggregate.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends DomainEntityRepository<User> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<User> findByUsername(String username);
   User getById(UUID id);

}
