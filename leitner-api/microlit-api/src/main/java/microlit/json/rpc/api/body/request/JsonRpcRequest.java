package microlit.json.rpc.api.body.request;

import microlit.json.rpc.api.body.JsonRpcBody;
import microlit.json.rpc.api.body.response.JsonRpcResponse;
import microlit.json.rpc.api.processor.JsonRpcRequestProcessor;

public interface JsonRpcRequest extends JsonRpcBody {
    JsonRpcResponse processWith(JsonRpcRequestProcessor jsonRpcRequestProcessor);
}
