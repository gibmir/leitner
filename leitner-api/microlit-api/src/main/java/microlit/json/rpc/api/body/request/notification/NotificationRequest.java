package microlit.json.rpc.api.body.request.notification;

import microlit.json.rpc.api.body.AbstractJsonRpcBody;
import microlit.json.rpc.api.body.request.JsonRpcRequest;
import microlit.json.rpc.api.body.response.JsonRpcResponse;
import microlit.json.rpc.api.processor.JsonRpcRequestProcessor;

public class NotificationRequest extends AbstractJsonRpcBody implements JsonRpcRequest<NotificationRequest> {
    private String method;
    private Object[] params;

    public NotificationRequest() {
        super();
    }

    private NotificationRequest(String method, Object[] params) {
        super();
        this.method = method;
        this.params = params;
    }

    public String getMethodName() {
        return method;
    }

    public Object[] getParams() {
        return params;
    }

    public static NotificationRequest create(String method, Object[] params) {
        return new NotificationRequest(method, params);
    }

    @Override
    public JsonRpcResponse processWith(JsonRpcRequestProcessor jsonRpcRequestProcessor) {
        return jsonRpcRequestProcessor.process(this);
    }
}
