package ecomhub.authservice.adapter.output.persistence.repositories.role;

import ecomhub.authservice.adapter.output.persistence.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface RoleJpaRepository extends JpaRepository<RoleEntity, UUID> {
    boolean existsByName(String name);

    @Query("SELECT r.id FROM RoleEntity r WHERE r.name = :name")
    UUID findIdByName(String name);
}
