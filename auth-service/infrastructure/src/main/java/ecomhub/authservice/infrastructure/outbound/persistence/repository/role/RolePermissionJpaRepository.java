package ecomhub.authservice.infrastructure.outbound.persistence.repository.role;

import ecomhub.authservice.infrastructure.outbound.persistence.entity.RolePermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RolePermissionJpaRepository extends JpaRepository<RolePermissionEntity, UUID> {
}
