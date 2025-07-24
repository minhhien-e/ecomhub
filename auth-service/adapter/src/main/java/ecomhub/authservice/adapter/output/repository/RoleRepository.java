package ecomhub.authservice.adapter.output.repository;

import ecomhub.authservice.application.port.repository.RoleRepositoryPort;
import ecomhub.authservice.domain.entity.Role;
import ecomhub.authservice.infrastructure.data.persistence.entity.RoleEntity;
import ecomhub.authservice.infrastructure.data.persistence.entity.RolePermissionEntity;
import ecomhub.authservice.infrastructure.data.persistence.entity.id.RolePermissionId;
import ecomhub.authservice.infrastructure.data.persistence.mapper.RolePersistenceMapper;
import ecomhub.authservice.infrastructure.data.persistence.repository.RoleJpaRepository;
import ecomhub.authservice.infrastructure.data.persistence.repository.RolePermissionJpaRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RoleRepository implements RoleRepositoryPort {
    private final RoleJpaRepository roleJpaRepository;
    private final RolePermissionJpaRepository rolePermissionJpaRepository;

    @Override
    public Role save(Role role) {
        var roleEntity = roleJpaRepository.save(RolePersistenceMapper.toEntity(role));
        return RolePersistenceMapper.toDomain(roleEntity);
    }

    @Override
    public boolean existsByName(String name) {
        return roleJpaRepository.existsByName(name);
    }

    @Override
    public Optional<Role> findByName(String name) {
        var entity = roleJpaRepository.findByName(name);
        return entity.map(RolePersistenceMapper::toDomain);
    }

    @Override
    public Optional<Role> findById(@NotNull UUID id) {
        Optional<RoleEntity> roleEntity = roleJpaRepository.findById(id);
        return roleEntity.map(RolePersistenceMapper::toDomain);
    }

    @Override
    public void grantPermissions(UUID roleId, Set<UUID> permissionIds) {
        Set<RolePermissionEntity> entities = permissionIds.stream()
                .map(pid -> RolePermissionEntity.builder()
                        .id(new RolePermissionId(roleId, pid))
                        .build())
                .collect(Collectors.toSet());
        rolePermissionJpaRepository.saveAll(entities);
    }

    @Override
    public void revokePermissions(UUID roleId, Set<UUID> permissionIds) {
        Set<RolePermissionEntity> entities = permissionIds.stream()
                .map(pid -> RolePermissionEntity.builder()
                        .id(new RolePermissionId(roleId, pid))
                        .build())
                .collect(Collectors.toSet());
        rolePermissionJpaRepository.deleteAll(entities);
    }
}

