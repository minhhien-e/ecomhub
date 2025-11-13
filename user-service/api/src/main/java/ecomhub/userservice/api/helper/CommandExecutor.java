package ecomhub.userservice.api.helper;

import ecomhub.userservice.api.dto.response.ApiResponse;
import ecomhub.userservice.application.dto.command.base.Command;
import ecomhub.userservice.application.port.in.bus.MediatorBusPort;
import ecomhub.userservice.application.port.in.provider.CurrentUserProviderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class CommandExecutor {
    private final MediatorBusPort mediatorBusPort;
    private final CurrentUserProviderPort currentUserProviderPort;

    public <R, C> ResponseEntity<ApiResponse<?>> sendCommand(Function<R, C> mapper, R request, HttpStatus status) {
        var command = mapper.apply(request);
        return handleCommand(command, status);
    }

    // Command có userId
    public <R, C> ResponseEntity<ApiResponse<?>> sendCommandWithCurrentUser(BiFunction<UUID, R, C> mapper, R request, HttpStatus status) {
        var userId = currentUserProviderPort.getCurrentUserId();
        var command = mapper.apply(userId, request);
        return handleCommand(command, status);
    }

    private <C> ResponseEntity<ApiResponse<?>> handleCommand(C command, HttpStatus status) {
        var result = mediatorBusPort.sendCommand((Command<?>) command);
        return ResponseEntity.status(status).body(ApiResponse.success(status.value(), result));
    }
}
