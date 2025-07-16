package ecomhub.authservice.adapter.input.rest;

import ecomhub.authservice.adapter.input.mapper.AccountAdapterMapper;
import ecomhub.authservice.adapter.input.request.RegisterBasicRequest;
import ecomhub.authservice.application.bus.ICommandBus;
import ecomhub.authservice.application.bus.IQueryBus;
import ecomhub.authservice.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AccountRestAdapter {
    private final ICommandBus commandBus;
    private final IQueryBus queryBus;
    private final AccountAdapterMapper mapper;


    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@Valid @RequestBody RegisterBasicRequest request) {
        commandBus.dispatch(mapper.toCommand(request));
        return ResponseEntity.ok(ApiResponse.success(null, "Đăng ký thành công"));
    }

}
