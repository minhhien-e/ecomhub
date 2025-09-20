package ecomhub.authservice.infrastructure.inbound.bus;

import ecomhub.authservice.application.port.bus.ICommandBus;
import ecomhub.authservice.application.command.abstracts.ICommand;
import ecomhub.authservice.application.command.abstracts.ICommandHandler;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CommandBus implements ICommandBus {
    private final Map<Class<? extends ICommand>, ICommandHandler<? extends ICommand>> handlerMap = new HashMap<>();

    @Autowired
    public CommandBus(List<ICommandHandler<?>> handlerList) {
        handlerList.forEach(
                (handler) -> handlerMap.put(getCommand(AopProxyUtils.ultimateTargetClass(handler)), handler)
        );
    }

    @SuppressWarnings("unchecked")
    private Class<? extends ICommand> getCommand(Class<?> handler) {
        for (Type type : handler.getGenericInterfaces()) {
            if (type instanceof ParameterizedType parameterizedType) {
                Type rawType = parameterizedType.getRawType();
                if (rawType instanceof Class<?> rawClass && ICommandHandler.class.isAssignableFrom(rawClass)) {
                    Type commandType = parameterizedType.getActualTypeArguments()[0];
                    if (commandType instanceof Class<?> commandClass)
                        return (Class<? extends ICommand>) commandClass;
                }
            }
        }
        throw new IllegalArgumentException("Không thể xác định kiểu ICommand cho handler: " + handler);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void dispatch(ICommand command) {
        ICommandHandler<ICommand> handler = (ICommandHandler<ICommand>) handlerMap.get(command.getClass());
        handler.handle(command);
    }
}
