package microlit.json.rpc.api.body.request.positional;

import microlit.json.rpc.api.body.AbstractJsonRpcBody;
import microlit.json.rpc.api.body.JsonRpcIdSupplier;
import microlit.json.rpc.api.body.request.JsonRpcRequest;
import microlit.json.rpc.api.body.response.JsonRpcResponse;
import microlit.json.rpc.api.processor.JsonRpcRequestProcessor;

public class PositionalRequest extends AbstractJsonRpcBody implements JsonRpcRequest<PositionalRequest>, JsonRpcIdSupplier {
    private String method;
    private Object[] params;
    private String id;

    public PositionalRequest() {
        super();
    }

    private PositionalRequest(String id, String method, Object[] params) {
        super();
        this.id = id;
        this.method = method;
        this.params = params;
    }

    public String getMethodName() {
        return method;
    }

    public Object[] getParams() {
        return params;
    }

    public static PositionalRequest createWithStringId(String id, String method, Object[] params) {
        return new PositionalRequest(id, method, params);
    }

    public static PositionalRequest createWithNumericId(int id, String method, Object[] params) {
        return new PositionalRequest(Integer.toString(id), method, params);
    }

    public static PositionalRequest createWithNullId(String method, Object[] params) {
        return new PositionalRequest(JSON_RPC_NULL_ID, method, params);
    }

    @Override
    public JsonRpcResponse processWith(JsonRpcRequestProcessor jsonRpcRequestProcessor) {
        return jsonRpcRequestProcessor.process(this);
    }

    @Override
    public String getId() {
        return id;
    }
}
