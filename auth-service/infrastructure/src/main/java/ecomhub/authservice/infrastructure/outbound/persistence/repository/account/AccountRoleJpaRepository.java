package ecomhub.authservice.infrastructure.outbound.persistence.repository.account;

import ecomhub.authservice.infrastructure.outbound.persistence.entity.AccountRoleEntity;
import ecomhub.authservice.infrastructure.outbound.persistence.entity.id.AccountRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRoleJpaRepository extends JpaRepository<AccountRoleEntity, AccountRoleId> {
}
