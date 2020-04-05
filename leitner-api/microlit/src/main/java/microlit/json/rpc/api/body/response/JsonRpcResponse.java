package microlit.json.rpc.api.body.response;

import microlit.json.rpc.api.processor.JsonRpcResponseProcessor;

public interface JsonRpcResponse  {
    void processWith(JsonRpcResponseProcessor jsonRpcResponseProcessor);
}
