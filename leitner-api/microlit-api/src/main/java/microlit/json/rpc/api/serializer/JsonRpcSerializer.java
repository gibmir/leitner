package microlit.json.rpc.api.serializer;


import microlit.json.rpc.api.body.request.JsonRpcRequest;
import microlit.json.rpc.api.body.response.JsonRpcResponse;

public interface JsonRpcSerializer {
    byte[] serializeRequest(JsonRpcRequest<?> jsonRpcRequest);

    byte[] serializeResponse(JsonRpcResponse jsonRpcResponse);

    JsonRpcRequest<?> deserializeRequest(byte[] payload);

    JsonRpcResponse deserializeResponse(byte[] payload);

}
