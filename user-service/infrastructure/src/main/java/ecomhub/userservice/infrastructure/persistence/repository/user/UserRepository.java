package ecomhub.userservice.infrastructure.persistence.repository.user;

import ecomhub.userservice.infrastructure.persistence.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface UserRepository extends MongoRepository<User, UUID> {
}
