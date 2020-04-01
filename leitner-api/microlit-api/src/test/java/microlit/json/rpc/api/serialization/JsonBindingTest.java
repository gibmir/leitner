package microlit.json.rpc.api.serialization;

import microlit.json.rpc.api.body.response.error.ErrorResponse;
import microlit.json.rpc.api.body.response.error.JsonRpcError;
import microlit.json.rpc.api.body.response.success.SuccessResponse;
import org.junit.jupiter.api.Test;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import static microlit.json.rpc.api.environment.TestEnvironment.NOTIFICATION_REQUEST;
import static microlit.json.rpc.api.environment.TestEnvironment.POSITIONAL_REQUEST;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonBindingTest {

    private static final Jsonb JSONB = JsonbBuilder.create();
    public static final ErrorResponse ERROR_RESPONSE = ErrorResponse.createWithStringId("asdasd", -32100, "cool");
    public static final SuccessResponse SUCCESS_RESPONSE = SuccessResponse.createWithNumericId(123, "result");

    @Test
    void testNotificationRequest() {
        final String notification = JSONB.toJson(NOTIFICATION_REQUEST);
        assertTrue(notification.contains(NOTIFICATION_REQUEST.getJsonRpcProtocolVersion()));
        assertTrue(notification.contains(NOTIFICATION_REQUEST.getMethodName()));
        for (Object parameter : NOTIFICATION_REQUEST.getParameters()) {
            assertTrue(notification.contains(parameter.toString()));
        }
    }

    @Test
    void testPositionalRequest() {
        final String request = JSONB.toJson(POSITIONAL_REQUEST);
        assertTrue(request.contains(POSITIONAL_REQUEST.getJsonRpcProtocolVersion()));
        assertTrue(request.contains(POSITIONAL_REQUEST.getId()));
        assertTrue(request.contains(POSITIONAL_REQUEST.getMethodName()));
        for (Object parameter : POSITIONAL_REQUEST.getParameters()) {
            assertTrue(request.contains(parameter.toString()));
        }
    }

    @Test
    void testErrorResponse() {
        final String error = JSONB.toJson(ERROR_RESPONSE);
        assertTrue(error.contains(ERROR_RESPONSE.getJsonRpcProtocolVersion()));
        assertTrue(error.contains(ERROR_RESPONSE.getId()));
        final JsonRpcError jsonRpcError = ERROR_RESPONSE.getJsonRpcError();
        assertTrue(error.contains(jsonRpcError.getMessage()));
        assertTrue(error.contains(String.valueOf(jsonRpcError.getCode())));
    }

    @Test
    void testSuccessResponse() {
        final String success = JSONB.toJson(SUCCESS_RESPONSE);
        assertTrue(success.contains(SUCCESS_RESPONSE.getJsonRpcProtocolVersion()));
        assertTrue(success.contains(SUCCESS_RESPONSE.getId()));
        assertTrue(success.contains(SUCCESS_RESPONSE.getResult().toString()));
    }
}
