package leitner.card.amqp.consumer.response;

import com.orbitz.consul.Consul;
import com.orbitz.consul.HealthClient;
import com.orbitz.consul.KeyValueClient;
import com.rabbitmq.client.BasicProperties;
import com.rabbitmq.client.Channel;
import microlit.json.rpc.api.body.response.error.ErrorResponse;
import microlit.json.rpc.api.body.response.notification.NotificationResponse;
import microlit.json.rpc.api.body.response.success.SuccessResponse;
import microlit.json.rpc.api.processor.JsonRpcResponseProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.json.bind.Jsonb;
import java.util.Optional;

public class CardResponseProcessor implements JsonRpcResponseProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(CardResponseProcessor.class);
    private final Jsonb jsonb;
    private final Channel channel;
    private final Consul consul;

    public CardResponseProcessor(Jsonb jsonb, Channel channel, Consul consul) {
        this.jsonb = jsonb;
        this.channel = channel;
        this.consul = consul;
    }

    @Override
    public void handle(ErrorResponse errorResponse) {
        final KeyValueClient keyValueClient = consul.keyValueClient();
        final Optional<String> valueAsString = keyValueClient.getValueAsString("card-reply-queue");
//        valueAsString.ifPresent();
        final byte[] bytes = jsonb.toJson(errorResponse).getBytes();
//        channel.basicPublish("", "", null, bytes);
    }

    @Override
    public void handle(NotificationResponse notificationResponse) {
        LOG.debug("Notification request was processed by server. Response is:[{}] ", notificationResponse);
    }

    @Override
    public void handle(SuccessResponse successResponse) {
        final byte[] bytes = jsonb.toJson(successResponse).getBytes();
//        channel.basicPublish("", "", null, bytes);
    }
}
