package leitner.research.rabbit;

import com.rabbitmq.client.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.containers.output.OutputFrame;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.TimeoutException;

@Testcontainers
class RabbitTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitTest.class);
    private static final Slf4jLogConsumer LOG_CONSUMER = new Slf4jLogConsumer(LOGGER);
    @Container
    private static final RabbitMQContainer RABBIT_MQ_CONTAINER = new RabbitMQContainer();
    public static final String PASSWORD = "guest";
    public static final String USERNAME = "guest";
    public static final String VIRTUAL_HOST = "/";
    public static final Set<String> ROLE_ADMIN = Collections.singleton("administrator");
    public static final String TEST_QUEUE = "test-queue";
    public static final String TEST_PAYLOAD = "testPayload";

    @BeforeAll
    static void beforeAll() {
        RABBIT_MQ_CONTAINER.followOutput(LOG_CONSUMER, OutputFrame.OutputType.STDOUT);
        RABBIT_MQ_CONTAINER.withExposedPorts(4369, 5672, 15672, 25672, 35197)
                .withVolumesFrom(RABBIT_MQ_CONTAINER, BindMode.READ_ONLY)
                .withUser(USERNAME, PASSWORD, ROLE_ADMIN)
                .withVhost(VIRTUAL_HOST)
                .withPermission(VIRTUAL_HOST, USERNAME, ".*", ".*", ".*")
                .start();

    }

    @Test
    void smoke() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RABBIT_MQ_CONTAINER.getContainerIpAddress());
        factory.setPort(RABBIT_MQ_CONTAINER.getMappedPort(5672));
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);
        publish(factory);
        consume(factory);
    }

    private void publish(ConnectionFactory factory) throws IOException, TimeoutException {
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(TEST_QUEUE, false, false, false, Collections.emptyMap());
            System.out.println("Publish");
            channel.basicPublish("", TEST_QUEUE, null, TEST_PAYLOAD.getBytes());
        }
    }

    private void consume(ConnectionFactory factory) throws IOException, TimeoutException {
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                           byte[] body) {
                    System.out.println(new String(body));
                }
            };
            channel.basicConsume(TEST_QUEUE, true, consumer);
        }
    }


    @AfterAll
    static void afterAll() {
        RABBIT_MQ_CONTAINER.stop();
    }
}
