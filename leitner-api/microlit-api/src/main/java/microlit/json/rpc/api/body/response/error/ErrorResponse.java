package microlit.json.rpc.api.body.response.error;

import microlit.json.rpc.api.body.AbstractJsonRpcBody;
import microlit.json.rpc.api.body.JsonRpcIdSupplier;
import microlit.json.rpc.api.body.response.JsonRpcResponse;

public class ErrorResponse extends AbstractJsonRpcBody implements JsonRpcResponse, JsonRpcIdSupplier {
    private int code;
    private String message;
    private String id;

    public ErrorResponse() {
        super();
    }

    private ErrorResponse(String id, int code, String message) {
        super();
        this.id = id;
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
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

    @Override
    public String toString() {
        return "ErrorResponseBody{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", jsonrpc='" + jsonrpc + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    @Override
    public String getId() {
        return id;
    }
}
