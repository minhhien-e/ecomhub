package ecomhub.authservice.infrastructure.outbound.persistence.repository.role.impl;

import ecomhub.authservice.common.exception.concrete.role.RoleNotFoundException;
import ecomhub.authservice.domain.entity.Permission;
import ecomhub.authservice.domain.entity.Role;
import ecomhub.authservice.domain.repository.RoleRepositoryPort;
import ecomhub.authservice.infrastructure.outbound.persistence.entity.RolePermissionEntity;
import ecomhub.authservice.infrastructure.outbound.persistence.entity.id.RolePermissionId;
import ecomhub.authservice.infrastructure.outbound.persistence.mapper.PermissionMapper;
import ecomhub.authservice.infrastructure.outbound.persistence.mapper.RoleMapper;
import ecomhub.authservice.infrastructure.outbound.persistence.repository.role.RoleJpaRepository;
import ecomhub.authservice.infrastructure.outbound.persistence.repository.role.RolePermissionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
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
        var roleEntity = roleJpaRepository.save(RoleMapper.toEntity(role));
        return RoleMapper.toDomain(roleEntity);
    }

    @Override
    public boolean existsByName(String name) {
        return roleJpaRepository.existsByName(name);
    }

    @Override
    public boolean existsByKey(String key) {
        return roleJpaRepository.existsByKey(key);
    }

    @Override
    public Role getByKey(String key) {
        var entity = roleJpaRepository.findByKey(key).orElseThrow(RoleNotFoundException::new);
        return RoleMapper.toDomain(entity);
    }


    @Override
    public Role getById(UUID id) {
        var entity = roleJpaRepository.findById(id).orElseThrow(RoleNotFoundException::new);
        return RoleMapper.toDomain(entity);
    }


    @Override
    public int updateDescription(Role role) {
        return roleJpaRepository.updateDescription(role.getId(), role.getDescription().orElse(null));
    }

    @Override
    public int updateLevel(Role role) {
        return roleJpaRepository.updateLevel(role.getId(), role.getLevel().getValue());
    }

    @Override
    public int updateName(Role role) {
        return roleJpaRepository.updateName(role.getId(), role.getName().getValue());
    }
    @Override
    public int updateStatus(Role role) {
        return roleJpaRepository.updateStatus(role.getId(),role.getStatus().getValue());
    }

    @Override
    public void deleteById(UUID id) {
        roleJpaRepository.deleteById(id);
    }

    @Override
    public void grantPermissions(Role role, Set<Permission> permissions) {
        Set<RolePermissionEntity> entities = permissions.stream()
                .map(p -> RolePermissionEntity.builder()
                        .id(new RolePermissionId(role.getId(), p.getId()))
                        .role(RoleMapper.toEntity(role))
                        .permission(PermissionMapper.toEntity(p))
                        .build())
                .collect(Collectors.toSet());
        rolePermissionJpaRepository.saveAll(entities);
    }

    @Override
    public void revokePermissions(Role role, Set<Permission> permissions) {
        Set<RolePermissionEntity> entities = permissions.stream()
                .map(p -> RolePermissionEntity.builder()
                        .id(new RolePermissionId(role.getId(), p.getId()))
                        .role(RoleMapper.toEntity(role))
                        .permission(PermissionMapper.toEntity(p))
                        .build())
                .collect(Collectors.toSet());
        rolePermissionJpaRepository.deleteAll(entities);
    }

    @Override
    public List<Role> findAll() {
        return roleJpaRepository.findAll().stream().map(RoleMapper::toDomain).toList();
    }


}

