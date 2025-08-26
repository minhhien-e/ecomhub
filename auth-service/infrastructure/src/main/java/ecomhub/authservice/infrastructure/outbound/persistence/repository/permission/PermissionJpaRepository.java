package ecomhub.authservice.infrastructure.outbound.persistence.repository.permission;

import ecomhub.authservice.domain.entity.Permission;
import ecomhub.authservice.infrastructure.outbound.persistence.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface PermissionJpaRepository extends JpaRepository<PermissionEntity, UUID> {
    boolean existsByKey(String key);

    List<Permission> findAllByKeyIn(Set<String> permissionKeys);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE PermissionEntity p SET p.description = :newDescription WHERE p.id = :id")
    int updateDescription(UUID id, String newDescription);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE PermissionEntity p SET p.name = :newName WHERE p.id = :id")
    int updateName(UUID id, String newName);

    Optional<PermissionEntity> findByKey(String permissionKey);
}
