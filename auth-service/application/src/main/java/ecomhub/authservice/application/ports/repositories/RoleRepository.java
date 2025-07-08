package ecomhub.authservice.application.ports.repositories;

import ecomhub.authservice.domain.entities.Role;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public interface RoleRepository {
    void save(Role role);

    boolean existsByName(String name);

    UUID findIdByName(String name);
}
