package ecomhub.authservice.domain.repository;

import ecomhub.authservice.domain.entity.Permission;
import ecomhub.authservice.domain.entity.Role;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface RoleRepositoryPort {
    Role save(Role role);

    boolean existsByName(String name);

    boolean existsByKey(String key);


    Role getByKey(String key);

    Role getById(UUID id);

    int updateDescription(Role role);

    int updateLevel(Role role);

    int updateName(Role role);

    void grantPermissions(Role role, Set<Permission> permissions);

    void revokePermissions(Role role,Set<Permission> permissions);

    List<Role> findAll();

    int updateStatus(Role role);

    void deleteById(UUID id);
}
