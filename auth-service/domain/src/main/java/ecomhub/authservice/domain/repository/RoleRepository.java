package ecomhub.authservice.domain.repository;

import ecomhub.authservice.domain.model.Role;

public interface RoleRepository {
    void save(Role role);

    boolean existsByName(String name);
}
