package ecomhub.authservice.infrastructure.data.persistence.repository;

import ecomhub.authservice.infrastructure.data.persistence.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface RoleJpaRepository extends JpaRepository<RoleEntity, UUID> {
    boolean existsByName(String name);

    @Query("SELECT r.id FROM RoleEntity r WHERE r.name = :name")
    Optional<UUID> findIdByName(String name);
}
