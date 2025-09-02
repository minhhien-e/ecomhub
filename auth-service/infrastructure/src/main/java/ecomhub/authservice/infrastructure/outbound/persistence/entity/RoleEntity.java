package ecomhub.authservice.infrastructure.outbound.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "role",
        indexes = {
                @Index(name = "idx_role_key", columnList = "key"),
                @Index(name = "idx_role_status", columnList = "status")
        }
)
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class RoleEntity {
    @Id
    private UUID id;
    @Column(nullable = false, unique = true, length = 100)
    private String name;
    @Column(nullable = false, unique = true, length = 100)
    private String key;
    @Column(nullable = false, length = 50)
    private String status;
    @Column(nullable = false, length = 50)
    private String type;
    @Lob
    @Column(length = 500)
    private String description;
    @Column(nullable = false)
    private int level;
    //mapping
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<AccountRoleEntity> accountRoles = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<RolePermissionEntity> rolePermissions = new HashSet<>();

}
