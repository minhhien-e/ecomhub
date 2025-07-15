package ecomhub.authservice.infrastructure.data.persistence.entity.id;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountRoleId implements Serializable {
    private UUID accountId;
    private UUID roleId;
}
