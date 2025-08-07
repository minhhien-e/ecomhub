package ecomhub.authservice.infrastructure.data.persistence.repository;

import ecomhub.authservice.infrastructure.data.persistence.entity.RoleEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleJpaRepository extends JpaRepository<RoleEntity, UUID> {
    boolean existsByName(String name);

    Optional<RoleEntity> findByName(String name);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE RoleEntity r SET r.active = :active WHERE r.id = :id")
    int updateActive(@Param("id") UUID id, @Param("active") boolean active);

    @Override
    @EntityGraph(attributePaths = {"rolePermissions.permission"})
    @NonNull
    Optional<RoleEntity> findById(@NonNull UUID id);

    @Query("""
            SELECT DISTINCT r FROM RoleEntity r
            JOIN r.accountRoles ac
            JOIN FETCH r.rolePermissions rp
            JOIN FETCH rp.permission
            WHERE ac.id.accountId = :accountId
            AND r.level > :level
            """)
    List<RoleEntity> findByAccountIdAndLevelGreaterThan(
            @Param("accountId") UUID accountId,
            @Param("level") int level
    );
}
