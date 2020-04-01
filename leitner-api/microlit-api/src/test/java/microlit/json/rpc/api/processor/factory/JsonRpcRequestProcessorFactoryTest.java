package microlit.json.rpc.api.processor.factory;

import microlit.json.rpc.api.body.response.JsonRpcResponse;
import microlit.json.rpc.api.body.response.error.ErrorResponse;
import microlit.json.rpc.api.body.response.success.SuccessResponse;
import microlit.json.rpc.api.processor.JsonRpcRequestProcessor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static microlit.json.rpc.api.environment.TestEnvironment.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonRpcRequestProcessorFactoryTest {
    private static JsonRpcRequestProcessor processor;

    @BeforeAll
    static void beforeAll() throws NoSuchMethodException, IllegalAccessException {
        processor = JsonRpcRequestProcessorFactory.createProcessor(TestApi.class, new TestApiImpl());
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
}
