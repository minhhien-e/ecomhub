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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RoleRepository implements RoleRepositoryPort {
    private final RoleJpaRepository roleJpaRepository;
    private final RolePermissionJpaRepository rolePermissionJpaRepository;

    @Override
    public Role save(Role role) {
        try {
            var roleEntity = roleJpaRepository.save(RoleMapper.toEntity(role));
            return RoleMapper.toDomain(roleEntity);
        } catch (Exception e) {
            log.error("Error while saving role [{}]: {}", role.getName().getValue(), e.getMessage(), e);
            throw e;
        }
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
        return roleJpaRepository.findByKey(key)
                .map(RoleMapper::toDomain)
                .orElseThrow(() -> {
                    log.warn("Role not found with key [{}]", key);
                    return new RoleNotFoundException();
                });
    }

    @Override
    public Role getById(UUID id) {
        return roleJpaRepository.findById(id)
                .map(RoleMapper::toDomain)
                .orElseThrow(() -> {
                    log.warn("Role not found with id [{}]", id);
                    return new RoleNotFoundException();
                });
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
        try {
            roleJpaRepository.deleteById(id);
            log.info("Deleted role with id [{}]", id);
        } catch (Exception e) {
            log.error("Error deleting role [{}]: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void grantPermissions(Role role, Set<Permission> permissions) {
        try {
            Set<RolePermissionEntity> entities = permissions.stream()
                    .map(p -> RolePermissionEntity.builder()
                            .id(new RolePermissionId(role.getId(), p.getId()))
                            .role(RoleMapper.toEntity(role))
                            .permission(PermissionMapper.toEntity(p))
                            .build())
                    .collect(Collectors.toSet());

            rolePermissionJpaRepository.saveAll(entities);
            log.info("Granted [{}] permissions to role [{}]", permissions.size(), role.getId());
        } catch (Exception e) {
            log.error("Error granting permissions to role [{}]: {}", role.getId(), e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void revokePermissions(Role role, Set<Permission> permissions) {
        try {
            Set<RolePermissionEntity> entities = permissions.stream()
                    .map(p -> RolePermissionEntity.builder()
                            .id(new RolePermissionId(role.getId(), p.getId()))
                            .role(RoleMapper.toEntity(role))
                            .permission(PermissionMapper.toEntity(p))
                            .build())
                    .collect(Collectors.toSet());

            rolePermissionJpaRepository.deleteAll(entities);
            log.info("Revoked [{}] permissions from role [{}]", permissions.size(), role.getId());
        } catch (Exception e) {
            log.error("Error revoking permissions from role [{}]: {}", role.getId(), e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Role> findAll() {
        return roleJpaRepository.findAll().stream().map(RoleMapper::toDomain).toList();
    }


}

