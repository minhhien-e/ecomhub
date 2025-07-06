package ecomhub.authservice.infrastructure.persistence.entity;

import ecomhub.authservice.infrastructure.persistence.entity.id.RolePermissionId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "role_permission")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionEntity {
    @EmbeddedId
    private RolePermissionId id;
    //mapping
    @MapsId("roleId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;
    @MapsId("permissionId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_id", nullable = false)
    private PermissionEntity permission;
}
