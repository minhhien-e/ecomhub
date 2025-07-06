package ecomhub.authservice.infrastructure.persistence.repository.permission.impl;

import ecomhub.authservice.domain.model.Permission;
import ecomhub.authservice.domain.repository.PermissionRepository;
import ecomhub.authservice.infrastructure.mapper.PermissionMapper;
import ecomhub.authservice.infrastructure.persistence.entity.PermissionEntity;
import ecomhub.authservice.infrastructure.persistence.repository.permission.PermissionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PermissionRepositoryImpl implements PermissionRepository {
    private final PermissionJpaRepository permissionJpaRepository;

    @Override
    public Permission save(Permission permission) {
     PermissionEntity permissionEntity= permissionJpaRepository.save(PermissionMapper.toEntity(permission));
     return PermissionMapper.toDomain(permissionEntity);
    }

    @Override
    public boolean existsByName(String name) {
        return permissionJpaRepository.existsByName(name);
    }
}
