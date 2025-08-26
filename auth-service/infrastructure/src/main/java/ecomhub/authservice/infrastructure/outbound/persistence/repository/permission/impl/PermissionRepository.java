package ecomhub.authservice.infrastructure.outbound.persistence.repository.permission.impl;

import ecomhub.authservice.common.exception.concrete.permission.PermissionNotFoundException;
import ecomhub.authservice.domain.entity.Permission;
import ecomhub.authservice.domain.repository.PermissionRepositoryPort;
import ecomhub.authservice.infrastructure.outbound.persistence.mapper.PermissionMapper;
import ecomhub.authservice.infrastructure.outbound.persistence.repository.permission.PermissionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PermissionRepository implements PermissionRepositoryPort {
    private final PermissionJpaRepository permissionJpaRepository;

    @Override
    public boolean existsByKey(String key) {
        return permissionJpaRepository.existsByKey(key);
    }


    @Override
    public boolean existsById(UUID id) {
        return permissionJpaRepository.existsById(id);
    }

    @Override
    public List<Permission> findAllByKeyIn(Set<String> permissionKeys) {
        return permissionJpaRepository.findAllByKeyIn(permissionKeys);
    }

    @Override
    public List<Permission> findAll() {
        return permissionJpaRepository.findAll().stream().map(PermissionMapper::toDomain).toList();
    }

    @Override
    public int updateDescription(Permission permission) {
        return permissionJpaRepository.updateDescription(permission.getId(), permission.getDescription().orElse(null));
    }

    @Override
    public int updateName(Permission permission) {
        return permissionJpaRepository.updateName(permission.getId(), permission.getName().getValue());
    }

    @Override
    public Permission getById(UUID permissionId) {
        var entity = permissionJpaRepository.findById(permissionId)
                .orElseThrow(PermissionNotFoundException::new);
        return PermissionMapper.toDomain(entity);
    }

    @Override
    public Optional<Permission> findByKey(String permissionKey) {
        return permissionJpaRepository.findByKey(permissionKey).map(PermissionMapper::toDomain);
    }
}
