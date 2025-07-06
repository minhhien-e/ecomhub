package ecomhub.authservice.adapter.rest;

import ecomhub.authservice.adapter.dto.request.AddRoleRequest;
import ecomhub.authservice.adapter.facade.RoleFacade;
import ecomhub.authservice.adapter.mapper.RoleMapper;
import ecomhub.authservice.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class RoleController {
    private final RoleFacade roleFacade;
    private final RoleMapper roleMapper;

    @PostMapping("/add-role")
    public ResponseEntity<?> addRole(@Valid @RequestBody AddRoleRequest request) {
        roleFacade.addRole(roleMapper.toCommand(request));
        return ResponseEntity.ok(ApiResponse.success(null, "Thêm role thành công"));
    }
}
