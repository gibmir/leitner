package microlit.json.rpc.api.body.response.error;

import microlit.json.rpc.api.body.JsonRpcIdSupplier;
import microlit.json.rpc.api.body.response.AbstractIdentifiableResponse;
import microlit.json.rpc.api.body.response.JsonRpcResponse;
import microlit.json.rpc.api.processor.JsonRpcResponseProcessor;

import javax.json.bind.annotation.JsonbProperty;

public class ErrorResponse extends AbstractIdentifiableResponse implements JsonRpcResponse, JsonRpcIdSupplier {
    @JsonbProperty("error")
    private JsonRpcError jsonRpcError;

    public ErrorResponse() {
        super();
    }

    private ErrorResponse(String id, int code, String message) {
        super(id);
        this.jsonRpcError = new JsonRpcError(code, message);
    }

    public static ErrorResponse createWithStringId(String id, int code, String message) {
        return new ErrorResponse(id, code, message);
    }

    public static ErrorResponse createWithNumericId(int id, int code, String message) {
        return new ErrorResponse(Integer.toString(id), code, message);
    }

    public static ErrorResponse createWithNullId(int code, String message) {
        return new ErrorResponse(JSON_RPC_NULL_ID, code, message);
    }

    public JsonRpcError getJsonRpcError() {
        return jsonRpcError;
    }

    public void setJsonRpcError(JsonRpcError jsonRpcError) {
        this.jsonRpcError = jsonRpcError;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "jsonRpcError=" + jsonRpcError +
                ", jsonRpcProtocolVersion='" + jsonRpcProtocolVersion + '\'' +
                '}';
    }

    @Override
    public void processWith(JsonRpcResponseProcessor jsonRpcResponseProcessor) {
        jsonRpcResponseProcessor.handle(this);
    }
}
