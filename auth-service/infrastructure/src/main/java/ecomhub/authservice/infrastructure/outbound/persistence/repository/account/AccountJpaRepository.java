package ecomhub.authservice.infrastructure.outbound.persistence.repository.account;

import ecomhub.authservice.infrastructure.outbound.persistence.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface AccountJpaRepository extends JpaRepository<AccountEntity, UUID>, JpaSpecificationExecutor<AccountEntity> {
}
