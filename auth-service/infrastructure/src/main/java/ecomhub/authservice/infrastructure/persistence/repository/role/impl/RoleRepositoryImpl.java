package ecomhub.authservice.infrastructure.persistence.repository.role.impl;

import ecomhub.authservice.domain.model.Role;
import ecomhub.authservice.domain.repository.RoleRepository;
import ecomhub.authservice.infrastructure.mapper.RoleMapper;
import ecomhub.authservice.infrastructure.persistence.entity.AccountRoleEntity;
import ecomhub.authservice.infrastructure.persistence.entity.RoleEntity;
import ecomhub.authservice.infrastructure.persistence.entity.RolePermissionEntity;
import ecomhub.authservice.infrastructure.persistence.entity.id.AccountRoleId;
import ecomhub.authservice.infrastructure.persistence.entity.id.RolePermissionId;
import ecomhub.authservice.infrastructure.persistence.repository.role.RoleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {
    private final RoleJpaRepository roleJpaRepository;

    @Override
    public void save(Role role) {
        RoleEntity roleEntity = roleJpaRepository.save(RoleMapper.toEntity(role));
        Set<RolePermissionId> rolePermissionIds = buildRolePermissionIds(role.getPermissionIds(), roleEntity.getId());
        Set<RolePermissionEntity> rolePermissionEntities = buildRolePermissions(rolePermissionIds);
        roleEntity.setRolePermissions(rolePermissionEntities);
        roleJpaRepository.save(roleEntity);
    }

    @Override
    public boolean existsByName(String name) {
        return roleJpaRepository.existsByName(name);
    }

    /**
     * Tạo danh sách các RolePermissionId từ tập hợp permissionIds và roleId
     *
     * @param permissionIds   Tập hợp ID của các permission
     * @param roleId ID của tài khoản
     * @return Tập hợp các RolePermissionId dùng để tạo id cho RolePermission
     */
    private Set<RolePermissionId> buildRolePermissionIds(Set<UUID> permissionIds, UUID roleId) {
        return permissionIds.stream()
                .map(permissionId -> RolePermissionId.builder()
                        .roleId(roleId)
                        .permissionId(permissionId)
                        .build())
                .collect(Collectors.toSet());
    }

    /**
     * Tạo danh sách các RolePermissionEntity từ tập hợp rolePermissionIds
     *
     * @param rolePermissionIds   Tập hợp ID của RolePermissionEntity
     * @return Tập hợp các RolePermission dùng để ánh xạ quan hệ role-permission
     */
    private Set<RolePermissionEntity> buildRolePermissions(Set<RolePermissionId> rolePermissionIds) {
        return rolePermissionIds.stream()
                .map(id -> RolePermissionEntity.builder().id(id).build())
                .collect(Collectors.toSet());
    }
}

