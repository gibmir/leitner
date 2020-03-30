package microlit.json.rpc.api.body.response.notification;

import microlit.json.rpc.api.body.AbstractJsonRpcBody;
import microlit.json.rpc.api.body.response.JsonRpcResponse;

public class NotificationResponse extends AbstractJsonRpcBody implements JsonRpcResponse {
    private String method;

    public NotificationResponse() {
        super();
    }

    private NotificationResponse(String method) {
        super();
        this.method = method;
    }

    public static NotificationResponse create(String method) {
        return new NotificationResponse(method);
    }

    public String getMethodName() {
        return method;
    }

    @Override
    public String toString() {
        return "NotificationResponse{" +
                " jsonrpc='" + jsonrpc + '\'' +
                ", method='" + method + '\'' +
                '}';
    }
}
