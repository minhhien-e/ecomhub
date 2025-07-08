package ecomhub.authservice.adapter.output.persistence.repositories.permission.impl;

import ecomhub.authservice.domain.entities.Permission;
import ecomhub.authservice.application.ports.repositories.PermissionRepository;
import ecomhub.authservice.adapter.output.mapper.permission.PermissionPersistenceMapper;
import ecomhub.authservice.adapter.output.persistence.entity.PermissionEntity;
import ecomhub.authservice.adapter.output.persistence.repositories.permission.PermissionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PermissionRepositoryImpl implements PermissionRepository {
    private final PermissionJpaRepository permissionJpaRepository;

    @Override
    public Permission save(Permission permission) {
     PermissionEntity permissionEntity= permissionJpaRepository.save(PermissionPersistenceMapper.toEntity(permission));
     return PermissionPersistenceMapper.toDomain(permissionEntity);
    }

    @Override
    public boolean existsByName(String name) {
        return permissionJpaRepository.existsByName(name);
    }
}
