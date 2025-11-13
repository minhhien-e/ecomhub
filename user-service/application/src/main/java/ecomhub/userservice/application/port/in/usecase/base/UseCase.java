package ecomhub.userservice.application.port.in.usecase.base;

public interface UseCase<R, P> {
    R execute(P params);
}
