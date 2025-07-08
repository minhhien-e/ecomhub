package ecomhub.authservice.adapter.output.persistence.repositories.permission;

import ecomhub.authservice.adapter.output.persistence.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PermissionJpaRepository extends JpaRepository<PermissionEntity, UUID> {
    boolean existsByName(String name);
}
