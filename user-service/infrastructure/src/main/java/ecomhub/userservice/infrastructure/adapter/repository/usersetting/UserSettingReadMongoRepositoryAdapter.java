package ecomhub.userservice.infrastructure.adapter.repository.usersetting;

import ecomhub.userservice.application.dto.readmodel.UserSettingReadModel;
import ecomhub.userservice.application.port.out.repository.UserSettingReadRepository;
import ecomhub.userservice.domain.exception.usersetting.UserSettingNotFoundException;
import ecomhub.userservice.infrastructure.mapper.UserSettingMapper;
import ecomhub.userservice.infrastructure.persistence.entity.UserSettingEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserSettingReadMongoRepositoryAdapter implements UserSettingReadRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public UserSettingReadModel getByUserId(UUID userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        var entity = mongoTemplate.findOne(query, UserSettingEntity.class);
        return Optional.ofNullable(entity).map(UserSettingMapper::toReadModel).orElseThrow(()-> new UserSettingNotFoundException(userId));
    }
}
