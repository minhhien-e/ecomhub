package ecomhub.authservice.infrastructure.data.persistence.entity;

import ecomhub.authservice.infrastructure.data.persistence.entity.id.RolePermissionId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "role_permission")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
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
