package ecomhub.userservice.infrastructure.adapter.bus;

import ecomhub.userservice.application.dto.base.Request;
import ecomhub.userservice.application.port.in.bus.MediatorBusPort;
import ecomhub.userservice.domain.exception.base.DomainException;
import ecomhub.userservice.infrastructure.exception.mapper.DomainToHttpExceptionMapper;
import io.github.mediatR.api.Bus;
import io.github.mediatR.core.BusRequestWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MediatRBusAdapter implements MediatorBusPort {
    private final Bus bus;
    private final DomainToHttpExceptionMapper exceptionMapper;

    @Override
    @Transactional
    public <R> R send(Request<R> request) {
        try {
            return bus.send(BusRequestWrapper.of(request));
        } catch (DomainException e) {
            throw exceptionMapper.map(e);
        }
    }
}
