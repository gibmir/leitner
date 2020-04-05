package leitner.card.amqp.consumer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import microlit.json.rpc.api.body.request.JsonRpcRequest;
import microlit.json.rpc.api.body.request.positional.PositionalRequest;
import microlit.json.rpc.api.body.response.JsonRpcResponse;
import microlit.json.rpc.api.processor.JsonRpcRequestProcessor;
import microlit.json.rpc.api.processor.JsonRpcResponseProcessor;
import microlit.json.rpc.api.serializer.JsonRpcSerializer;

import java.io.IOException;

public class CardAmqpController extends DefaultConsumer {
    private final JsonRpcRequestProcessor jsonRpcRequestProcessor;
    private final JsonRpcResponseProcessor jsonRpcResponseProcessor;
    private final JsonRpcSerializer jsonRpcSerializer;

    public CardAmqpController(Channel channel, JsonRpcRequestProcessor jsonRpcRequestProcessor,
                              JsonRpcResponseProcessor jsonRpcResponseProcessor, JsonRpcSerializer jsonRpcSerializer) {
        super(channel);
        this.jsonRpcRequestProcessor = jsonRpcRequestProcessor;
        this.jsonRpcResponseProcessor = jsonRpcResponseProcessor;
        this.jsonRpcSerializer = jsonRpcSerializer;
    }

    @Override
    public void handleDelivery(String consumerTag,
                               Envelope envelope,
                               AMQP.BasicProperties properties,
                               byte[] body) {
        final JsonRpcRequest jsonRpcRequest = jsonRpcSerializer.deserializeRequest(body, PositionalRequest.class);
        final JsonRpcResponse jsonRpcResponse = jsonRpcRequest.processWith(jsonRpcRequestProcessor);
        jsonRpcResponse.processWith(jsonRpcResponseProcessor);
    }
}
