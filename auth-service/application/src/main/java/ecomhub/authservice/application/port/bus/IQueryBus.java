package ecomhub.authservice.application.port.bus;

import ecomhub.authservice.application.query.abstracts.IQuery;

public interface IQueryBus {
    <R> R dispatch(IQuery<R> query);
}
