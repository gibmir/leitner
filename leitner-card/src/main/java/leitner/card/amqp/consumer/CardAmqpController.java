package leitner.card.amqp.consumer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import microlit.json.rpc.api.body.request.positional.PositionalRequest;
import microlit.json.rpc.api.body.response.JsonRpcResponse;
import microlit.json.rpc.api.processor.JsonRpcRequestProcessor;
import microlit.json.rpc.api.serializer.JsonRpcSerializer;

import java.io.IOException;

public class CardAmqpController extends DefaultConsumer {
    private final JsonRpcRequestProcessor jsonRpcRequestProcessor;
    private final JsonRpcSerializer jsonRpcSerializer;

    public CardAmqpController(Channel channel, JsonRpcRequestProcessor jsonRpcRequestProcessor,
                              JsonRpcSerializer jsonRpcSerializer) {
        super(channel);
        this.jsonRpcRequestProcessor = jsonRpcRequestProcessor;
        this.jsonRpcSerializer = jsonRpcSerializer;
    }

    @Override
    public void handleDelivery(String consumerTag,
                               Envelope envelope,
                               AMQP.BasicProperties properties,
                               byte[] body) throws IOException {
        final PositionalRequest jsonRpcRequest = (PositionalRequest) jsonRpcSerializer.deserializeRequest(body);
        final JsonRpcResponse jsonRpcResponse = jsonRpcRequestProcessor.process(jsonRpcRequest);
        final byte[] payload = jsonRpcSerializer.serializeResponse(jsonRpcResponse);

    }
}
