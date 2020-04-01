package microlit.json.rpc.api.body.response.notification;

import microlit.json.rpc.api.body.AbstractJsonRpcBody;
import microlit.json.rpc.api.body.response.JsonRpcResponse;
import microlit.json.rpc.api.processor.JsonRpcResponseProcessor;

public class NotificationResponse extends AbstractJsonRpcBody implements JsonRpcResponse {
    private String methodName;

    public NotificationResponse() {
        super();
    }

    private NotificationResponse(String methodName) {
        super();
        this.methodName = methodName;
    }

    public static NotificationResponse create(String method) {
        return new NotificationResponse(method);
    }

    public String getMethodName() {
        return methodName;
    }

    @Override
    public String toString() {
        return "NotificationResponse{" +
                " jsonrpc='" + jsonRpcProtocolVersion + '\'' +
                ", method='" + methodName + '\'' +
                '}';
    }

    @Override
    public void processWith(JsonRpcResponseProcessor jsonRpcResponseProcessor) {
        jsonRpcResponseProcessor.handle(this);
    }
}
