package ecomhub.authservice.infrastructure.outbound.persistence.repository.role.impl;

import ecomhub.authservice.domain.repository.RoleRepositoryPort;
import ecomhub.authservice.domain.entity.Role;
import ecomhub.authservice.infrastructure.outbound.persistence.entity.PermissionEntity;
import ecomhub.authservice.infrastructure.outbound.persistence.entity.RoleEntity;
import ecomhub.authservice.infrastructure.outbound.persistence.entity.RolePermissionEntity;
import ecomhub.authservice.infrastructure.outbound.persistence.entity.id.RolePermissionId;
import ecomhub.authservice.infrastructure.outbound.persistence.converter.RoleConverter;
import ecomhub.authservice.infrastructure.outbound.persistence.repository.role.RoleJpaRepository;
import ecomhub.authservice.infrastructure.outbound.persistence.repository.role.RolePermissionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RoleRepository implements RoleRepositoryPort {
    private final RoleJpaRepository roleJpaRepository;
    private final RolePermissionJpaRepository rolePermissionJpaRepository;

    //region create
    @Override
    public Role save(Role role) {
        var roleEntity = roleJpaRepository.save(RoleConverter.toEntity(role));
        return RoleConverter.toDomain(roleEntity);
    }

    //endregion
    //region exist
    @Override
    public boolean existsByName(String name) {
        return roleJpaRepository.existsByName(name);
    }

    //endregion
    //region find
    @Override
    public Optional<Role> findByName(String name) {
        var entity = roleJpaRepository.findByName(name);
        return entity.map(RoleConverter::toDomain);
    }


    @Override
    public Optional<Role> findById(UUID id) {
        Optional<RoleEntity> roleEntity = roleJpaRepository.findById(id);
        return roleEntity.map(RoleConverter::toDomain);
    }

    //endregion
    //region update
    @Override
    public int updateActive(UUID id, boolean isActive) {
        return roleJpaRepository.updateActive(id, isActive);
    }

    @Override
    public int updateDescription(UUID id, String newDescription) {
        return roleJpaRepository.updateDescription(id, newDescription);
    }

    @Override
    public int updateLevel(UUID id, Integer newLevel) {
        return roleJpaRepository.updateLevel(id, newLevel);
    }

    @Override
    public int updateName(UUID id, String newName) {
        return roleJpaRepository.updateName(id, newName);
    }

    //endregion
    //region permission
    @Override
    public void grantPermissions(UUID roleId, Set<UUID> permissionIds) {
        Set<RolePermissionEntity> entities = permissionIds.stream()
                .map(pid -> RolePermissionEntity.builder()
                        .id(new RolePermissionId(roleId, pid))
                        .role(RoleEntity.builder().id(roleId).build())
                        .permission(PermissionEntity.builder().id(pid).build())
                        .build())
                .collect(Collectors.toSet());
        rolePermissionJpaRepository.saveAll(entities);
    }

    @Override
    public void revokePermissions(UUID roleId, Set<UUID> permissionIds) {
        Set<RolePermissionEntity> entities = permissionIds.stream()
                .map(pid -> RolePermissionEntity.builder()
                        .id(new RolePermissionId(roleId, pid))
                        .role(RoleEntity.builder().id(roleId).build())
                        .permission(PermissionEntity.builder().id(pid).build())
                        .build())
                .collect(Collectors.toSet());
        rolePermissionJpaRepository.deleteAll(entities);
    }

    //endregion
    //region Find All
    @Override
    public List<Role> findAll() {
        return roleJpaRepository.findAll().stream().map(RoleConverter::toDomain).toList();
    }
    //endregion
}

