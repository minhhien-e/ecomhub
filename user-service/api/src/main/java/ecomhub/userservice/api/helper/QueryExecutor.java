package ecomhub.userservice.api.helper;

import ecomhub.userservice.api.dto.response.ApiResponse;
import ecomhub.userservice.application.dto.query.base.Query;
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
public class QueryExecutor {
    private final MediatorBusPort mediatorBusPort;
    private final CurrentUserProviderPort currentUserProviderPort;

    public <R, Q> ResponseEntity<ApiResponse<?>> sendQuery(Function<R, Q> mapper, R request, HttpStatus status) {
        var query = mapper.apply(request);
        return handleQuery(query, status);
    }

    // Query có userId
    public <R, Q> ResponseEntity<ApiResponse<?>> sendQueryWithCurrentUser(BiFunction<UUID, R, Q> mapper, R request, HttpStatus status) {
        var userId = currentUserProviderPort.getCurrentUserId();
        var query = mapper.apply(userId, request);
        return handleQuery(query, status);
    }

    private <Q> ResponseEntity<ApiResponse<?>> handleQuery(Q query, HttpStatus status) {
        var result = mediatorBusPort.sendQuery((Query<?>) query);
        return ResponseEntity.status(status).body(ApiResponse.success(status.value(), result));
    }
}
