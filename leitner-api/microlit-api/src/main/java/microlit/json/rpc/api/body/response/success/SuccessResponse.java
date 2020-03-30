package microlit.json.rpc.api.body.response.success;

import microlit.json.rpc.api.body.AbstractJsonRpcBody;
import microlit.json.rpc.api.body.JsonRpcIdSupplier;
import microlit.json.rpc.api.body.response.JsonRpcResponse;

public class SuccessResponse extends AbstractJsonRpcBody implements JsonRpcResponse, JsonRpcIdSupplier {
    private String id;
    private Object result;

    public SuccessResponse() {
        super();
    }

    private SuccessResponse(String id, Object result) {
        super();
        this.id = id;
        this.result = result;
    }

    public Object getResult() {
        return result;
    }

    public static SuccessResponse createWithStringId(String id, Object result) {
        return new SuccessResponse(id, result);
    }

    public static SuccessResponse createWithNumericId(int id, Object result) {
        return new SuccessResponse(Integer.toString(id), result);
    }

    public static SuccessResponse createWithNullId(Object result) {
        return new SuccessResponse(JSON_RPC_NULL_ID, result);
    }

    @Override
    public String toString() {
        return "SuccessResponseBody{" +
                " jsonrpc='" + jsonrpc + '\'' +
                ", id='" + id + '\'' +
                ", result=" + result +
                '}';
    }

    @Override
    public String getId() {
        return id;
    }
}
