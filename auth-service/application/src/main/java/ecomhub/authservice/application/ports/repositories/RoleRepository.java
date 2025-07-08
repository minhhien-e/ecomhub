package ecomhub.authservice.application.ports.repositories;

import ecomhub.authservice.domain.entities.Role;

public interface RoleRepository {
    void save(Role role);

    boolean existsByName(String name);
}
