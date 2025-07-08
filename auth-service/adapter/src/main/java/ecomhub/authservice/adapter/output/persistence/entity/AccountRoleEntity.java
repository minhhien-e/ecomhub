package ecomhub.authservice.adapter.output.persistence.entity;

import ecomhub.authservice.adapter.output.persistence.entity.id.AccountRoleId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Table(name = "account_role")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountRoleEntity {
    @EmbeddedId
    private AccountRoleId id;
    //mapping
    @MapsId("accountId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity account;
    @MapsId("roleId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;
}
