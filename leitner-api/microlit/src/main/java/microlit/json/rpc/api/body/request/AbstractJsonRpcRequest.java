package microlit.json.rpc.api.body.request;

import microlit.json.rpc.api.body.AbstractJsonRpcBody;

import javax.json.bind.annotation.JsonbProperty;

public abstract class AbstractJsonRpcRequest extends AbstractJsonRpcBody {
    @JsonbProperty("method")
    protected String methodName;
    @JsonbProperty("params")
    protected Object[] parameters;

    public AbstractJsonRpcRequest() {
        super();
    }

    public AbstractJsonRpcRequest(String methodName, Object[] parameters) {
        super();
        this.methodName = methodName;
        this.parameters = parameters;
    }

    public String getMethodName() {
        return methodName;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}
