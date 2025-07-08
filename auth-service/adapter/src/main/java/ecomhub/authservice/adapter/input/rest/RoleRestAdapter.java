package ecomhub.authservice.adapter.input.rest;

import ecomhub.authservice.adapter.input.request.AddRoleRequest;
import ecomhub.authservice.adapter.input.facade.RoleFacade;
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
public class RoleRestAdapter {
    private final RoleFacade roleFacade;

    @Operation(summary = "Thêm vai trò")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Thêm vai trò thành công")
    })
    @PostMapping("/add-role")
    public ResponseEntity<?> addRole(@Valid @RequestBody AddRoleRequest request) {
        roleFacade.addRole(request);
        return ResponseEntity.ok(ApiResponse.success(null, "Thêm vai trò thành công"));
    }
}
