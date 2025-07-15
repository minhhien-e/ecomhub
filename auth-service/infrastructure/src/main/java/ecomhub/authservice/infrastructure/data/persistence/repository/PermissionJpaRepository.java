package ecomhub.authservice.infrastructure.data.persistence.repository;

import ecomhub.authservice.infrastructure.data.persistence.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PermissionJpaRepository extends JpaRepository<PermissionEntity, UUID> {
    boolean existsByName(String name);
}
