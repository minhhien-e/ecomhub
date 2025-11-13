package ecomhub.userservice.infrastructure.adapter.bus;

import ecomhub.userservice.application.dto.query.base.Query;
import io.github.admiralxy.mediatr.api.Request;

public record MediatorQuery<R, Q extends Query<R>>(Q query) implements Request<R> {
}
