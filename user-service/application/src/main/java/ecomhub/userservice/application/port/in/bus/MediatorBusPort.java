package ecomhub.userservice.application.port.in.bus;


import ecomhub.userservice.application.dto.base.Request;

public interface MediatorBusPort {
    <R> R send(Request<R> request);
}
