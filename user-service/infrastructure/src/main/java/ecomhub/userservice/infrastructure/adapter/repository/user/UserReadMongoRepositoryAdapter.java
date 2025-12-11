package ecomhub.userservice.infrastructure.adapter.repository.user;

import ecomhub.userservice.application.dto.readmodel.UserReadModel;
import ecomhub.userservice.application.port.out.repository.UserReadRepository;
import ecomhub.userservice.domain.exception.user.UserNotFoundException;
import ecomhub.userservice.infrastructure.mapper.UserMapper;
import ecomhub.userservice.infrastructure.persistence.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserReadMongoRepositoryAdapter implements UserReadRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public UserReadModel getById(UUID id) {
        UserEntity entity = mongoTemplate.findById(id, UserEntity.class);
        return Optional.ofNullable(entity).map(UserMapper::toReadModel).orElseThrow(() -> new UserNotFoundException(id));
    }
}
