package microlit.json.rpc.api.processor.factory;

import microlit.json.rpc.api.body.request.notification.NotificationRequest;
import microlit.json.rpc.api.body.request.positional.PositionalRequest;
import microlit.json.rpc.api.body.response.JsonRpcResponse;
import microlit.json.rpc.api.body.response.error.ErrorResponse;
import microlit.json.rpc.api.body.response.notification.NotificationResponse;
import microlit.json.rpc.api.body.response.success.SuccessResponse;
import microlit.json.rpc.api.processor.JsonRpcRequestProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

public class ServiceFactory {

    public static <T> JsonRpcRequestProcessor createProcessor(Class<T> serviceInterface,
                                                              T service) throws NoSuchMethodException, IllegalAccessException {
        final NamedMethodHandle[] methodHandles = resolveMethodHandlesFor(serviceInterface);
        return new DefaultJsonRequestProcessor<>(methodHandles, service);
    }

    public static NamedMethodHandle[] resolveMethodHandlesFor(Class<?> serviceInterface) throws NoSuchMethodException, IllegalAccessException {
        final MethodHandles.Lookup publicLookup = MethodHandles.publicLookup();

        final Method[] methods = serviceInterface.getMethods();

        NamedMethodHandle[] methodHandles = new NamedMethodHandle[methods.length];
        for (int i = 0; i < methods.length; i++) {
            final Method method = methods[i];
            final String name = method.getName();
            final MethodType methodType = MethodType.methodType(method.getReturnType(), method.getParameterTypes());
            final MethodHandle methodHandle = publicLookup.findVirtual(serviceInterface, name, methodType);
            methodHandles[i] = new NamedMethodHandle(methodHandle.asSpreader(Object[].class, method.getParameterCount()),
                    name);
        }
        return methodHandles;
    }

    private static class NamedMethodHandle {
        private final MethodHandle methodHandle;
        private final String methodName;

        private NamedMethodHandle(MethodHandle methodHandle, String methodName) {
            this.methodHandle = methodHandle;
            this.methodName = methodName;
        }

        public Object invokeWithArguments(Object... args) throws Throwable {
            return methodHandle.invokeWithArguments(args);
        }
    }

    private static class DefaultJsonRequestProcessor<S> implements JsonRpcRequestProcessor {
        public static final Logger LOGGER = LoggerFactory.getLogger(DefaultJsonRequestProcessor.class);
        private final NamedMethodHandle[] namedMethodHandles;
        private final S service;

        public DefaultJsonRequestProcessor(NamedMethodHandle[] namedMethodHandles, S service) {
            this.namedMethodHandles = namedMethodHandles;
            this.service = service;
        }

        @Override
        public JsonRpcResponse process(PositionalRequest positionalRequest) {
            try {
                return invokeMethod(positionalRequest);
            } catch (Throwable throwable) {
                final String message = String.format("Exception occurred while invoking method [%s]. Message is:%s",
                        positionalRequest.getMethodName(), throwable.getMessage());
                return ErrorResponse.createWithStringId(positionalRequest.getId(), -32000, message);
            }
        }

        private JsonRpcResponse invokeMethod(PositionalRequest jsonRpcRequest) throws Throwable {
            for (final NamedMethodHandle methodHandle : namedMethodHandles) {
                if (methodHandle.methodName.equals(jsonRpcRequest.getMethodName())) {
                    final Object result = methodHandle.invokeWithArguments(service, jsonRpcRequest.getParameters());
                    return SuccessResponse.createWithStringId(jsonRpcRequest.getId(), result);
                }
            }
            return ErrorResponse.createWithStringId(jsonRpcRequest.getId(),
                    -32000, "Method " + jsonRpcRequest.getMethodName() + " is unsupported");

        }

        @Override
        public JsonRpcResponse process(NotificationRequest notificationRequest) {
            try {
                return invokeMethod(notificationRequest);
            } catch (Throwable throwable) {
                LOGGER.error("Exception occurred while invoking method [{}]. Message is:{}",
                        notificationRequest.getMethodName(), throwable.getMessage());
                return NotificationResponse.create(notificationRequest.getMethodName());
            }
        }

        private JsonRpcResponse invokeMethod(NotificationRequest notificationRequest) throws Throwable {
            for (final NamedMethodHandle methodHandle : namedMethodHandles) {
                if (methodHandle.methodName.equals(notificationRequest.getMethodName())) {
                    final Object result = methodHandle.invokeWithArguments(service, notificationRequest.getParameters());
                    if (result != null) {
                        LOGGER.warn("Result isn't null for notification request:[{}]", notificationRequest);
                    }
                    return NotificationResponse.create(notificationRequest.getMethodName());
                }
            }
            LOGGER.error("Method [{}] is unsupported. Exception: ", notificationRequest.getMethodName(),
                    new UnsupportedOperationException());
            return NotificationResponse.create(notificationRequest.getMethodName());
        }
    }
}
