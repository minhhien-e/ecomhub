package ecomhub.authservice.infrastructure.inbound.web.rest;

import ecomhub.authservice.application.port.bus.ICommandBus;
import ecomhub.authservice.application.port.bus.IQueryBus;
import ecomhub.authservice.common.dto.ApiResponse;
import ecomhub.authservice.infrastructure.inbound.web.dto.request.permisison.AddPermissionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.client.JwtBearerOAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.server.resource.authentication.JwtBearerTokenAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static ecomhub.authservice.infrastructure.inbound.web.mapper.PermissionRequestMapper.toCommand;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class PermissionRestAdapter {
    private final ICommandBus commandBus;
    private final IQueryBus queryBus;

    @PreAuthorize("hasAuthority('permission.create')")
    @PostMapping("/permission")
    public ResponseEntity<?> addPermission( @RequestBody AddPermissionRequest request) {
        commandBus.dispatch(toCommand(request));
        return ResponseEntity.ok(ApiResponse.success(null, "Thêm quyền thành công"));
    }
}
