package ecomhub.authservice.infrastructure.data.persistence.repository;

import ecomhub.authservice.infrastructure.data.persistence.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface RoleJpaRepository extends JpaRepository<RoleEntity, UUID> {
    boolean existsByName(String name);

    Optional<RoleEntity> findByName(String name);
}
