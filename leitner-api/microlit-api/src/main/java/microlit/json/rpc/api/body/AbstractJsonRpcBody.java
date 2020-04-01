package microlit.json.rpc.api.body;

import javax.json.bind.annotation.JsonbProperty;

public abstract class AbstractJsonRpcBody implements JsonRpcBody {
    public static final String JSON_RPC_PROTOCOL_VERSION = "2.0";
    public static final String JSON_RPC_NULL_ID = "null";

    @JsonbProperty("jsonrpc")
    protected String jsonRpcProtocolVersion;

    public AbstractJsonRpcBody() {
        jsonRpcProtocolVersion = JSON_RPC_PROTOCOL_VERSION;
    }

    public String getJsonRpcProtocolVersion() {
        return jsonRpcProtocolVersion;
    }
}
