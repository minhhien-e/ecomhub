package ecomhub.authservice.adapter.input.rest;

import ecomhub.authservice.adapter.input.facade.AccountFacade;
import ecomhub.authservice.adapter.input.request.RegisterBasicRequest;
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
public class AccountRestAdapter {
    private final AccountFacade accountFacade;

    @Operation(summary = "Đăng ký tài khoản với password")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Đăng ký thành công")
    })
    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@Valid @RequestBody RegisterBasicRequest request) {
        accountFacade.registerAccount(request);
        return ResponseEntity.ok(ApiResponse.success(null, "Đăng ký thành công"));
    }
}
