package microlit.json.rpc.api.body.response;

import microlit.json.rpc.api.body.AbstractJsonRpcBody;
import microlit.json.rpc.api.body.JsonRpcIdSupplier;

public abstract class AbstractIdentifiableResponse extends AbstractJsonRpcBody implements JsonRpcIdSupplier {
    private String id;

    public AbstractIdentifiableResponse() {
        super();
    }

    public AbstractIdentifiableResponse(String id) {
        super();
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

}
