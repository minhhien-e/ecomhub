package ecomhub.authservice.infrastructure.data.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "permission",
        indexes = {
                @Index(name = "idx_permission_key", columnList = "key"),
        }
)
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class PermissionEntity {
    @Id
    private UUID id;
    @Column(nullable = false, unique = true, length = 100)
    private String name;
    @Column(nullable = false, unique = true, length = 100)
    private String key;
    @Lob
    @Column
    private String description;
    //maping
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "permission", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RolePermissionEntity> accountPermissions;
}
