package ecomhub.authservice.infrastructure.inbound.web.rest;

import ecomhub.authservice.application.port.bus.ICommandBus;
import ecomhub.authservice.application.port.bus.IQueryBus;
import ecomhub.authservice.common.dto.ApiResponse;
import ecomhub.authservice.infrastructure.inbound.web.dto.request.account.RegisterBasicRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static ecomhub.authservice.infrastructure.inbound.web.mapper.AccountRequestMapper.toCommand;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AccountRestAdapter {
    private final ICommandBus commandBus;
    private final IQueryBus queryBus;

    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@RequestBody RegisterBasicRequest request) {
        commandBus.dispatch(toCommand(request));
        return ResponseEntity.ok(ApiResponse.success(null, "Đăng ký thành công"));
    }

}
