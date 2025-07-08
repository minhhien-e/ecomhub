package ecomhub.authservice.adapter.input.rest;

import ecomhub.authservice.adapter.input.request.AddPermissionRequest;
import ecomhub.authservice.adapter.input.facade.PermissionFacade;
import ecomhub.authservice.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
public class PermissionRestAdapter {
    private final PermissionFacade permissionFacade;
    @Operation(summary = "Thêm quyền hạn")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Thêm quyền thành công")
    })
    @PostMapping("/add-permission")
    public ResponseEntity<?> addPermission(@Valid @RequestBody AddPermissionRequest request) {
        permissionFacade.addPermission(request);
        return ResponseEntity.ok(ApiResponse.success(null, "Thêm quyền thành công"));
    }
}
