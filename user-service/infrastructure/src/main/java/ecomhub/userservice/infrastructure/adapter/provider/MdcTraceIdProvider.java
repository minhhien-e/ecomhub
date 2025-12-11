package ecomhub.userservice.infrastructure.adapter.provider;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import ecomhub.userservice.application.port.in.provider.CurrentTraceIdProvider;

@Component
public class MdcTraceIdProvider implements CurrentTraceIdProvider {
    public String getCurrentTraceId() {
        return MDC.get("traceId");
    }
}
