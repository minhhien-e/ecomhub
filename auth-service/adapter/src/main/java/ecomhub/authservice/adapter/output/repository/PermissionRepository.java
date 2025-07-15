package ecomhub.authservice.adapter.output.repository;

import ecomhub.authservice.application.port.repository.PermissionRepositoryPort;
import ecomhub.authservice.domain.entity.Permission;
import ecomhub.authservice.infrastructure.data.persistence.mapper.PermissionPersistenceMapper;
import ecomhub.authservice.infrastructure.data.persistence.repository.PermissionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PermissionRepository implements PermissionRepositoryPort {
    private final PermissionJpaRepository permissionJpaRepository;

    @Override
    public Permission save(Permission permission) {
     var permissionEntity= permissionJpaRepository.save(PermissionPersistenceMapper.toEntity(permission));
     return PermissionPersistenceMapper.toDomain(permissionEntity);
    }

    @Override
    public boolean existsByName(String name) {
        return permissionJpaRepository.existsByName(name);
    }
}
