package microlit.json.rpc.api.processor.factory;

import microlit.json.rpc.api.body.request.notification.NotificationRequest;
import microlit.json.rpc.api.body.request.positional.PositionalRequest;
import microlit.json.rpc.api.body.response.JsonRpcResponse;
import microlit.json.rpc.api.body.response.error.ErrorResponse;
import microlit.json.rpc.api.body.response.success.SuccessResponse;
import microlit.json.rpc.api.processor.JsonRpcRequestProcessor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServiceFactoryTest {
    public static final String METHOD_NAME = "smoke";
    public static final String INCORRECT_METHOD_NAME = "incorrectMethodName";
    private static JsonRpcRequestProcessor processor;
    public static final Object[] ARGS = {1, "ihg"};
    public static final PositionalRequest POSITIONAL_REQUEST = PositionalRequest.createWithNumericId(1, METHOD_NAME,
            ARGS);
    public static final PositionalRequest POSITIONAL_REQUEST_INCORRECT_METHOD = PositionalRequest.createWithNumericId(1, INCORRECT_METHOD_NAME,
            ARGS);
    public static final PositionalRequest POSITIONAL_REQUEST_INCORRECT_ARGS = PositionalRequest.createWithNumericId(1, METHOD_NAME,
            new Object[]{});
    public static final NotificationRequest NOTIFICATION_REQUEST = NotificationRequest.create(METHOD_NAME,
            ARGS);
    public static final NotificationRequest INCORRECT_METHOD_NOTIFICATION = NotificationRequest.create(INCORRECT_METHOD_NAME,
            ARGS);
    public static final NotificationRequest INCORRECT_ARGS_NOTIFICATION = NotificationRequest.create(METHOD_NAME,
            new Object[]{});


    @BeforeAll
    static void beforeAll() throws NoSuchMethodException, IllegalAccessException {
        processor = ServiceFactory.createProcessor(TestApi.class, new TestApiImpl());
    }

    @Test
    void testPositionalRequestWithSuccessResponse() {
        final JsonRpcResponse jsonRpcResponse = processor.process(POSITIONAL_REQUEST);
        assertNotNull(jsonRpcResponse);
        assertTrue(jsonRpcResponse instanceof SuccessResponse);
    }

    @Test
    void testIncorrectMethodNamePositionalRequestWithErrorResponse() {
        final JsonRpcResponse jsonRpcResponse = processor.process(POSITIONAL_REQUEST_INCORRECT_METHOD);
        assertNotNull(jsonRpcResponse);
        assertTrue(jsonRpcResponse instanceof ErrorResponse);
    }

    @Test
    void testIncorrectArgsPositionalRequestWithErrorResponse() {
        final JsonRpcResponse jsonRpcResponse = processor.process(POSITIONAL_REQUEST_INCORRECT_ARGS);
        assertNotNull(jsonRpcResponse);
        assertTrue(jsonRpcResponse instanceof ErrorResponse);
    }

    @Test
    void testNotificationRequest() {
        final JsonRpcResponse responseToRequest = processor.process(NOTIFICATION_REQUEST);
        assertNotNull(responseToRequest);
    }

    @Test
    void testNotificationRequestWithIncorrectMethodName() {
        final JsonRpcResponse responseToRequestWithIncorrectMethodName = processor.process(INCORRECT_METHOD_NOTIFICATION);
        assertNotNull(responseToRequestWithIncorrectMethodName);
    }

    @Test
    void testNotificationRequestWithIncorrectArgs() {
        final JsonRpcResponse responseToRequestWithIncorrectArgs = processor.process(INCORRECT_ARGS_NOTIFICATION);
        assertNotNull(responseToRequestWithIncorrectArgs);
    }

    public interface TestApi {
        String smoke(Integer j, String string);
    }

    public static class TestApiImpl implements TestApi {

        @Override
        public String smoke(Integer i, String string) {
            return string + i;
        }
    }
}
