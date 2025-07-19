package ecomhub.authservice.application.query.interfaces;

public interface IQueryHandler<Q,R> {
    R handle(Q query);
}
