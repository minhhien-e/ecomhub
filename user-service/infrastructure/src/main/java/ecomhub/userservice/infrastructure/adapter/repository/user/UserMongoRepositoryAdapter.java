package ecomhub.userservice.infrastructure.adapter.repository.user;

import ecomhub.userservice.domain.entity.UserSetting;
import ecomhub.userservice.domain.exception.user.UserNotFoundException;
import ecomhub.userservice.infrastructure.mapper.UserSettingMapper;
import io.github.domain.aggregate.base.AggregateChild;
import io.github.domain.entity.base.DomainEntity;
import io.github.infrastructure.mongo.repository.base.AbstractAggregateMongoRepository;
import ecomhub.userservice.domain.aggregate.User;
import ecomhub.userservice.domain.repository.UserRepository;
import ecomhub.userservice.infrastructure.mapper.UserMapper;
import ecomhub.userservice.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UserMongoRepositoryAdapter extends AbstractAggregateMongoRepository<User, UserEntity> implements UserRepository {

    public UserMongoRepositoryAdapter(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    @Override
    public boolean existsByUsername(String username) {
        Query query = new Query(Criteria.where("username").is(username));
        return mongoTemplate.exists(query, UserEntity.class);
    }

    @Override
    public boolean existsByEmail(String email) {
        Query query = new Query(Criteria.where("email").is(email));
        return mongoTemplate.exists(query, UserEntity.class);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Query query = new Query(Criteria.where("username").is(username));
        UserEntity entity = mongoTemplate.findOne(query, UserEntity.class);
        return Optional.ofNullable(entity).map(UserMapper::toDomain);
    }

    @Override
    public User getById(UUID id) {
        Query query = new Query(Criteria.where("_id").is(id));
        UserEntity entity = mongoTemplate.findOne(query, UserEntity.class);
        return Optional.ofNullable(entity).map(UserMapper::toDomain).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public Class<?> getEntityClass() {
        return UserEntity.class;
    }

    @Override
    public Class<?> getChildEntityClass(AggregateChild child) {
        if (child instanceof UserSetting)
            return UserSetting.class;
        return null;
    }

    @Override
    public UserEntity toEntity(User aggregate) {
        return UserMapper.toEntity(aggregate);
    }

    @Override
    protected Object toChildEntity(DomainEntity entity) {
        if (entity instanceof UserSetting)
            return UserSettingMapper.toEntity((UserSetting) entity);
        return null;
    }
}
