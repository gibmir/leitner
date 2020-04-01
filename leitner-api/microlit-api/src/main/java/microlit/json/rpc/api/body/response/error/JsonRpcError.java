package microlit.json.rpc.api.body.response.error;

import javax.json.bind.annotation.JsonbProperty;

public class JsonRpcError {
    @JsonbProperty("code")
    private int code;
    @JsonbProperty("message")
    private String message;

    public JsonRpcError() {
    }

    public JsonRpcError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
