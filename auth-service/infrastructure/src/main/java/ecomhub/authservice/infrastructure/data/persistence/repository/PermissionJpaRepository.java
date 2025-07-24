package ecomhub.authservice.infrastructure.data.persistence.repository;

import ecomhub.authservice.domain.entity.Permission;
import ecomhub.authservice.infrastructure.data.persistence.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PermissionJpaRepository extends JpaRepository<PermissionEntity, UUID> {
    boolean existsByName(String name);

    List<Permission> findAllByKeyIn(List<String> permissionKeys);
}
