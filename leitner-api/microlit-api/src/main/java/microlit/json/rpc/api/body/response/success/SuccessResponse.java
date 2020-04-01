package microlit.json.rpc.api.body.response.success;

import microlit.json.rpc.api.body.AbstractJsonRpcBody;
import microlit.json.rpc.api.body.JsonRpcIdSupplier;
import microlit.json.rpc.api.body.response.AbstractIdentifiableResponse;
import microlit.json.rpc.api.body.response.JsonRpcResponse;

import javax.json.bind.annotation.JsonbProperty;

public class SuccessResponse extends AbstractIdentifiableResponse implements JsonRpcResponse, JsonRpcIdSupplier {
    @JsonbProperty("result")
    private Object result;

    public SuccessResponse() {
        super();
    }

    private SuccessResponse(String id, Object result) {
        super(id);
        this.result = result;
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

    public Object getResult() {
        return result;
    }

}
