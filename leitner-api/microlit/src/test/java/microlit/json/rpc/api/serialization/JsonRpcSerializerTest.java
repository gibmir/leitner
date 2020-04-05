package microlit.json.rpc.api.serialization;

import microlit.json.rpc.api.body.request.notification.NotificationRequest;
import microlit.json.rpc.api.body.request.positional.PositionalRequest;
import microlit.json.rpc.api.body.response.error.ErrorResponse;
import microlit.json.rpc.api.body.response.success.SuccessResponse;
import microlit.json.rpc.api.serializer.JsonRpcSerializer;
import org.junit.jupiter.api.Test;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import static microlit.json.rpc.api.environment.TestEnvironment.NOTIFICATION_REQUEST;
import static microlit.json.rpc.api.environment.TestEnvironment.POSITIONAL_REQUEST;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonRpcSerializerTest {
    private static final Jsonb JSONB = JsonbBuilder.create();
    private static final JsonRpcSerializer JSON_RPC_SERIALIZER = new JsonRpcSerializer(JSONB);
    public static final SuccessResponse SUCCESS_RESPONSE = SuccessResponse.createWithNumericId(1, "result");
    public static final ErrorResponse ERROR_RESPONSE = ErrorResponse.createWithNumericId(1, -32000, "test message");

    @Test
    void testPositionalRequest() {
        final byte[] bytes = assertDoesNotThrow(() -> JSON_RPC_SERIALIZER.serializeRequest(POSITIONAL_REQUEST));
        final PositionalRequest jsonRpcRequest = assertDoesNotThrow(() ->
                JSON_RPC_SERIALIZER.deserializeRequest(bytes, PositionalRequest.class));
        assertEquals(POSITIONAL_REQUEST.getId(), jsonRpcRequest.getId());
        assertEquals(POSITIONAL_REQUEST.getMethodName(), jsonRpcRequest.getMethodName());
        assertEquals(POSITIONAL_REQUEST.getJsonRpcProtocolVersion(), jsonRpcRequest.getJsonRpcProtocolVersion());
    }

    @Test
    void testNotificationRequest() {
        final byte[] bytes = assertDoesNotThrow(() -> JSON_RPC_SERIALIZER.serializeRequest(NOTIFICATION_REQUEST));
        final NotificationRequest notificationRequest = assertDoesNotThrow(() ->
                JSON_RPC_SERIALIZER.deserializeRequest(bytes, NotificationRequest.class));
        assertEquals(NOTIFICATION_REQUEST.getMethodName(), notificationRequest.getMethodName());
        assertEquals(NOTIFICATION_REQUEST.getJsonRpcProtocolVersion(), notificationRequest.getJsonRpcProtocolVersion());
    }

    @Test
    void testSuccessResponse() {
        final byte[] bytes = JSON_RPC_SERIALIZER.serializeResponse(SUCCESS_RESPONSE);
        final SuccessResponse successResponse = JSON_RPC_SERIALIZER.deserializeResponse(bytes, SuccessResponse.class);
        assertEquals(SUCCESS_RESPONSE.getId(), successResponse.getId());
        assertEquals(SUCCESS_RESPONSE.getResult(), successResponse.getResult());
        assertEquals(SUCCESS_RESPONSE.getJsonRpcProtocolVersion(), successResponse.getJsonRpcProtocolVersion());
    }

    @Test
    void testErrorResponse() {
        final byte[] bytes = JSON_RPC_SERIALIZER.serializeResponse(ERROR_RESPONSE);
        final ErrorResponse errorResponse = JSON_RPC_SERIALIZER.deserializeResponse(bytes, ErrorResponse.class);
        assertEquals(ERROR_RESPONSE.getId(), errorResponse.getId());
        assertEquals(ERROR_RESPONSE.getJsonRpcProtocolVersion(), errorResponse.getJsonRpcProtocolVersion());
    }
}
