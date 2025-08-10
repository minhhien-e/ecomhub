package ecomhub.authservice.infrastructure.outbound.persistence.repository.permission.impl;

import ecomhub.authservice.domain.repository.PermissionRepositoryPort;
import ecomhub.authservice.domain.entity.Permission;
import ecomhub.authservice.infrastructure.outbound.persistence.converter.PermissionConverter;
import ecomhub.authservice.infrastructure.outbound.persistence.repository.permission.PermissionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class PermissionRepository implements PermissionRepositoryPort {
    private final PermissionJpaRepository permissionJpaRepository;

    @Override
    public Permission save(Permission permission) {
     var permissionEntity= permissionJpaRepository.save(PermissionConverter.toEntity(permission));
     return PermissionConverter.toDomain(permissionEntity);
    }

    @Override
    public boolean existsByName(String name) {
        return permissionJpaRepository.existsByName(name);
    }

    @Override
    public List<Permission> findAllByKeyIn(Set<String> permissionKeys) {
        return permissionJpaRepository.findAllByKeyIn(permissionKeys);
    }
}
