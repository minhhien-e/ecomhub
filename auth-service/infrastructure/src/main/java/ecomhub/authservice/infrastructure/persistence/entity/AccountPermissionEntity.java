package ecomhub.authservice.infrastructure.persistence.entity;

import ecomhub.authservice.infrastructure.persistence.entity.id.AccountPermissionId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "account_permission")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountPermissionEntity {
    @EmbeddedId
    private AccountPermissionId id;
    //mapping
    @MapsId("accountId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity account;
    @MapsId("permissionId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_id", nullable = false)
    private PermissionEntity permission;
}
