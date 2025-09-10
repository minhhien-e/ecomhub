package ecomhub.authservice.application.query.permission.getall;

import ecomhub.authservice.application.dto.PermissionDto;
import ecomhub.authservice.application.mapper.PermissionMapper;
import ecomhub.authservice.application.query.abstracts.IQueryHandler;
import ecomhub.authservice.domain.repository.PermissionRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GetAllPermissionHandler implements IQueryHandler<GetAllPermissionQuery, List<PermissionDto>> {
    private final PermissionRepositoryPort permissionRepository;

    @Override
    public List<PermissionDto> handle(GetAllPermissionQuery query) {
        return permissionRepository.findAll().stream().map(PermissionMapper::toDto).toList();
    }
}
