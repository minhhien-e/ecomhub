package ecomhub.authservice.infrastructure.data.persistence.entity;

import ecomhub.authservice.infrastructure.data.persistence.entity.id.AccountRoleId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "account_role")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class AccountRoleEntity {
    @EmbeddedId
    private AccountRoleId id;
    //mapping
    @MapsId("accountId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity account;
    @MapsId("roleId")
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;
}
