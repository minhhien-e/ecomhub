package ecomhub.authservice.infrastructure.bus;

import ecomhub.authservice.application.bus.IQueryBus;
import ecomhub.authservice.application.query.interfaces.IQuery;
import ecomhub.authservice.application.query.interfaces.IQueryHandler;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class QueryBus implements IQueryBus {
    private final Map<Class<? extends IQuery<?>>, IQueryHandler<? extends IQuery<?>, ?>> queryMapping = new HashMap<>();

    @Autowired
    public QueryBus(List<IQueryHandler<? extends IQuery<?>, ?>> handlerList) {
        handlerList.forEach(
                handler ->
                        queryMapping.put(getQuery(AopProxyUtils.ultimateTargetClass(handler)), handler)
        );

    }

    @SuppressWarnings("unchecked")
    private Class<? extends IQuery<?>> getQuery(Class<?> handler) {
        //lấy interface kế thừa
        Type[] interfaceTypes = handler.getGenericInterfaces();
        for (Type interfaceType : interfaceTypes) {
            if (interfaceType instanceof ParameterizedType parameterizedType) {
                //Lấy kiểu gốc
                Type rawType = parameterizedType.getRawType();
                if (rawType instanceof Class<?> rawClass
                        && IQueryHandler.class.isAssignableFrom(rawClass)) {
                    //Lấy generic bên trong
                    Type queryType = parameterizedType.getActualTypeArguments()[0];
                    if (queryType instanceof Class<?>) {
                        return (Class<? extends IQuery<?>>) queryType;
                    }
                }
            }
        }
        throw new IllegalArgumentException("Không thể xác định kiểu IQuery cho handler: " + handler);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R> R dispatch(IQuery<R> query) {
        IQueryHandler<IQuery<R>, R> handler = (IQueryHandler<IQuery<R>, R>) queryMapping.get(query.getClass());
        return handler.handle(query);
    }
}
