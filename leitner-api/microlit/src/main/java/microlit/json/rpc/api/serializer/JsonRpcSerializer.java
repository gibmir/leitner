package microlit.json.rpc.api.serializer;


import microlit.json.rpc.api.body.request.JsonRpcRequest;
import microlit.json.rpc.api.body.response.JsonRpcResponse;

import javax.json.bind.Jsonb;

public class JsonRpcSerializer {
    private final Jsonb jsonb;

    public JsonRpcSerializer(Jsonb jsonb) {
        this.jsonb = jsonb;
    }

    public byte[] serializeRequest(JsonRpcRequest jsonRpcRequest) {
        return jsonb.toJson(jsonRpcRequest).getBytes();
    }

    public byte[] serializeResponse(JsonRpcResponse jsonRpcResponse) {
        return jsonb.toJson(jsonRpcResponse).getBytes();
    }

    public <T extends JsonRpcRequest> T deserializeRequest(byte[] payload, Class<T> requestType) {
        return jsonb.fromJson(new String(payload), requestType);
    }

    public <T extends JsonRpcResponse> T deserializeResponse(byte[] payload, Class<T> responseType) {
        return jsonb.fromJson(new String(payload), responseType);
    }
}
