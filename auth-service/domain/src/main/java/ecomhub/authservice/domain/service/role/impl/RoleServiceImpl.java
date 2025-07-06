package ecomhub.authservice.domain.service.role.impl;

import ecomhub.authservice.common.exception.RoleAlreadyExistsException;
import ecomhub.authservice.domain.model.Role;
import ecomhub.authservice.domain.repository.RoleRepository;
import ecomhub.authservice.domain.service.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public void addRole(Role role) {
        if (isRoleExists(role.getName())) {
            throw new RoleAlreadyExistsException();
        }
        roleRepository.save(role);
    }

    @Override
    public boolean isRoleExists(String name) {
        return roleRepository.existsByName(name);
    }
}
