package microlit.json.rpc.api.body.request.positional;

import microlit.json.rpc.api.body.JsonRpcIdSupplier;
import microlit.json.rpc.api.body.request.AbstractJsonRpcRequest;
import microlit.json.rpc.api.body.request.JsonRpcRequest;
import microlit.json.rpc.api.body.response.JsonRpcResponse;
import microlit.json.rpc.api.processor.JsonRpcRequestProcessor;

import java.util.Arrays;

public class PositionalRequest extends AbstractJsonRpcRequest implements JsonRpcRequest, JsonRpcIdSupplier {
    private String id;

    public PositionalRequest() {
        super();
    }

    private PositionalRequest(String id, String methodName, Object[] parameters) {
        super(methodName, parameters);
        this.id = id;
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

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "PositionalRequest{" +
                "id='" + id + '\'' +
                ", methodName='" + methodName + '\'' +
                ", parameters=" + Arrays.toString(parameters) +
                ", jsonRpcProtocolVersion='" + jsonRpcProtocolVersion + '\'' +
                '}';
    }
}
