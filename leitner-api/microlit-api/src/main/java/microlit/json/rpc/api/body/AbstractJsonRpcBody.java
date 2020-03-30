package microlit.json.rpc.api.body;

public abstract class AbstractJsonRpcBody implements JsonRpcBody {
    public static final String JSON_RPC_PROTOCOL_VERSION = "2.0";
    public static final String JSON_RPC_NULL_ID = "null";
    protected String jsonrpc;

    public AbstractJsonRpcBody() {
        jsonrpc = JSON_RPC_PROTOCOL_VERSION;
    }

    public String getJsonRpcProtocolVersion() {
        return jsonrpc;
    }
}
