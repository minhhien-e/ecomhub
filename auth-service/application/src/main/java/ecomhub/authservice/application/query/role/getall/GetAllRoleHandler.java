package ecomhub.authservice.application.query.role.getall;

import ecomhub.authservice.application.dto.RoleDto;
import ecomhub.authservice.application.mapper.RoleMapper;
import ecomhub.authservice.application.query.abstracts.IQueryHandler;
import ecomhub.authservice.domain.repository.RoleRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GetAllRoleHandler implements IQueryHandler<GetAllRoleQuery, List<RoleDto>> {
    private final RoleRepositoryPort roleRepository;

    @Override
    public List<RoleDto> handle(GetAllRoleQuery query) {
        return roleRepository.findAll().stream().map(RoleMapper::toDto).toList();
    }
}
