package ecomhub.authservice.application.bus;

import ecomhub.authservice.application.query.interfaces.IQuery;

public interface IQueryBus {
    <R> R dispatch(IQuery<R> query);
}
