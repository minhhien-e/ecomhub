package ecomhub.authservice.infrastructure.outbound.persistence.repository.role;

import ecomhub.authservice.infrastructure.outbound.persistence.entity.RoleEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.Optional;
import java.util.UUID;

public interface RoleJpaRepository extends JpaRepository<RoleEntity, UUID> {
    boolean existsByName(String name);

    boolean existsByKey(String key);

    @Override
    @EntityGraph(attributePaths = {"rolePermissions.permission"})
    @NonNull
    Optional<RoleEntity> findById(@NonNull UUID id);

    Optional<RoleEntity> findByKey(String name);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE RoleEntity r SET r.description = :newDescription WHERE r.id = :id")
    int updateDescription(UUID id, String newDescription);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE RoleEntity r SET r.level = :newLevel WHERE r.id = :id")
    int updateLevel(UUID id, Integer newLevel);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE RoleEntity r SET r.name = :newName WHERE r.id = :id")
    int updateName(UUID id, String newName);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE RoleEntity r SET r.status = :newStatus WHERE r.id = :id")
    int updateStatus(UUID id, String newStatus);

}
