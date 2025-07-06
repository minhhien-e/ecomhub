package ecomhub.authservice.adapter.rest;

import ecomhub.authservice.adapter.dto.request.AddPermissionRequest;
import ecomhub.authservice.adapter.facade.PermissionFacade;
import ecomhub.authservice.adapter.mapper.PermissionMapper;
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
public class PermissionController {
    private final PermissionFacade permissionFacade;
    private final PermissionMapper permissionMapper;

    @PostMapping("/add-permission")
    public ResponseEntity<?> addPermission(@Valid @RequestBody AddPermissionRequest request) {
        permissionFacade.addPermission(permissionMapper.toCommand(request));
        return ResponseEntity.ok(ApiResponse.success(null,"Thêm quyền thành công"));

    }
}
