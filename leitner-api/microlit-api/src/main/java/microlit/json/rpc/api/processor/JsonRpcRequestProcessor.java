package microlit.json.rpc.api.processor;


import microlit.json.rpc.api.body.request.notification.NotificationRequest;
import microlit.json.rpc.api.body.request.positional.PositionalRequest;
import microlit.json.rpc.api.body.response.JsonRpcResponse;

public interface JsonRpcRequestProcessor {
    JsonRpcResponse process(PositionalRequest positionalRequest);

    JsonRpcResponse process(NotificationRequest notificationRequest);
}
