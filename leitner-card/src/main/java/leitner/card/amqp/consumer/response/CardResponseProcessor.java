package leitner.card.amqp.consumer.response;

import microlit.json.rpc.api.body.response.error.ErrorResponse;
import microlit.json.rpc.api.body.response.notification.NotificationResponse;
import microlit.json.rpc.api.body.response.success.SuccessResponse;
import microlit.json.rpc.api.processor.JsonRpcResponseProcessor;

public class CardResponseProcessor implements JsonRpcResponseProcessor {
    @Override
    public void handle(ErrorResponse errorResponse) {

    }

    @Override
    public void handle(NotificationResponse notificationResponse) {

    }

    @Override
    public void handle(SuccessResponse successResponse) {

    }
}
