package ecomhub.authservice.application.query.abstracts;

public interface IQueryHandler<Q,R> {
    R handle(Q query);
}
