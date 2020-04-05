package microlit.json.rpc.api.processor;

import microlit.json.rpc.api.body.response.error.ErrorResponse;
import microlit.json.rpc.api.body.response.notification.NotificationResponse;
import microlit.json.rpc.api.body.response.success.SuccessResponse;

public interface JsonRpcResponseProcessor {
    void handle(ErrorResponse errorResponse);

    void handle(NotificationResponse notificationResponse);

    void handle(SuccessResponse successResponse);
}
