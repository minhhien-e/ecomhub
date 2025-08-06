package ecomhub.authservice.adapter.input.rest;

import ecomhub.authservice.adapter.input.mapper.RoleAdapterMapper;
import ecomhub.authservice.adapter.input.request.role.AddRoleRequest;
import ecomhub.authservice.application.bus.ICommandBus;
import ecomhub.authservice.application.bus.IQueryBus;
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
public class RoleRestAdapter {
    private final ICommandBus commandBus;
    private final IQueryBus queryBus;
    private final RoleAdapterMapper mapper;

    @PostMapping("/role")
    public ResponseEntity<?> addRole(@Valid @RequestBody AddRoleRequest request) {
        commandBus.dispatch(mapper.toCommand(request));
        return ResponseEntity.ok(ApiResponse.success(null, "Thêm vai trò thành công"));
    }
}
