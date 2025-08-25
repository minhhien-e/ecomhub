package ecomhub.authservice.infrastructure.outbound.persistence.repository.permission.impl;

import ecomhub.authservice.common.exception.concrete.permission.PermissionNotFoundException;
import ecomhub.authservice.domain.repository.PermissionRepositoryPort;
import ecomhub.authservice.domain.entity.Permission;
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
    public Permission save(Permission permission) {
        var permissionEntity = permissionJpaRepository.save(PermissionMapper.toEntity(permission));
        return PermissionMapper.toDomain(permissionEntity);
    }

    @Override
    public boolean existsByNameOrKey(String name, String key) {
        return permissionJpaRepository.existsByNameOrKey(name, key);
    }

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
    public void deleteById(UUID id) {
        permissionJpaRepository.deleteById(id);
    }

    @Override
    public int updateDescription(UUID id, String newDescription) {
        return permissionJpaRepository.updateDescription(id, newDescription);
    }

    @Override
    public int updateName(UUID id, String newName) {
        return permissionJpaRepository.updateName(id, newName);
    }

    @Override
    public Permission getById(UUID permissionId) {
        var entity= permissionJpaRepository.findById(permissionId)
                .orElseThrow(PermissionNotFoundException::new);
        return PermissionMapper.toDomain(entity);
    }

    @Override
    public Optional<Permission> findByKey(String permissionKey) {
        return permissionJpaRepository.findByKey(permissionKey).map(PermissionMapper::toDomain);
    }
}
