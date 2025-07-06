package ecomhub.authservice.adapter.rest;

import ecomhub.authservice.adapter.dto.request.RegisterBasicRequest;
import ecomhub.authservice.adapter.facade.AccountFacade;
import ecomhub.authservice.adapter.mapper.AccountMapper;
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
public class AccountController {
    private final AccountFacade accountFacade;
    private final AccountMapper accountMapper;

    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@Valid @RequestBody RegisterBasicRequest request) {
        accountFacade.registerAccount(accountMapper.toCommand(request));
        return ResponseEntity.ok(ApiResponse.success(null,"Đăng ký thành công"));
    }
}
