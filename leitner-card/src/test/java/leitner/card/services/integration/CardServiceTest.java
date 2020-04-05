package leitner.card.services.integration;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import leitner.card.api.model.Card;
import leitner.card.api.services.CardService;
import leitner.card.services.integration.containers.MongoDbContainer;
import leitner.card.services.mongo.MongoCardAdapter;
import leitner.card.services.mongo.MongoCardService;
import microlit.json.rpc.api.body.request.positional.PositionalRequest;
import microlit.json.rpc.api.body.response.JsonRpcResponse;
import microlit.json.rpc.api.body.response.error.ErrorResponse;
import microlit.json.rpc.api.body.response.success.SuccessResponse;
import microlit.json.rpc.api.processor.JsonRpcRequestProcessor;
import microlit.json.rpc.api.processor.factory.JsonRpcRequestProcessorFactory;
import org.bson.Document;
import org.junit.jupiter.api.*;

import static leitner.card.services.integration.containers.MongoDbContainer.DEFAULT_MONGO_PORT;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CardServiceTest {

    public static final String EXPECTED_QUESTION = "year";
    public static final String EXPECTED_ANSWER = "2020";
    public static final long SECOND_ID = 2L;
    private static CardService cardService;
    public static final long ID = 1L;
    private static MongoDbContainer mongoDbContainer;
    public static final PositionalRequest GET_REQUEST = PositionalRequest
            .createWithStringId("axsdwq123dsa", "getCardBy", new Object[]{ID});
    public static final PositionalRequest EMPTY_GET_REQUEST = PositionalRequest
            .createWithStringId("asdsd", "getCardBy", new Object[]{33L});
    public static final PositionalRequest INCORRECT_GET_REQUEST = PositionalRequest
            .createWithStringId("asdsd", "getCardBy", new Object[]{});
    private static JsonRpcRequestProcessor processor;

    @BeforeAll
    static void beforeAll() throws NoSuchMethodException, IllegalAccessException {
        mongoDbContainer = new MongoDbContainer();
        mongoDbContainer.withExposedPorts(DEFAULT_MONGO_PORT);
        mongoDbContainer.start();
        final MongoClient mongoClient = new MongoClient("localhost", mongoDbContainer.getPort());
        final MongoDatabase leitner = mongoClient.getDatabase("leitner");
        final MongoCollection<Document> cards = leitner.getCollection("cards");
        final Card card = new Card(ID, EXPECTED_QUESTION, EXPECTED_ANSWER);
        cards.insertOne(MongoCardAdapter.toDocument(card));
        cardService = new MongoCardService(cards);
        processor = JsonRpcRequestProcessorFactory.createProcessor(CardService.class, cardService);
    }

    @Test
    @Order(1)
    void testGetCardById() {
        Card card = cardService.getCardBy(ID);
        assertNotNull(card);
        assertEquals(ID, card.getId());
        assertEquals(EXPECTED_QUESTION, card.getQuestion());
        assertEquals(EXPECTED_ANSWER, card.getAnswer());
    }

    @Test
    @Order(2)
    void testSaveCard() {
        final Card card = new Card(SECOND_ID, "sky color", "blue");
        assertDoesNotThrow(() -> cardService.save(card));
        assertNotNull(cardService.getCardBy(SECOND_ID));
    }

    @Test
    @Order(3)
    void testUpdateCard() {
        Card card = cardService.getCardBy(ID);
        assertNotNull(card);
        String updatedAnswer = "2019";
        card.setAnswer(updatedAnswer);
        assertDoesNotThrow(() -> cardService.save(card));
        Card updatedCard = cardService.getCardBy(ID);
        assertNotNull(updatedCard);
        assertEquals(updatedAnswer, updatedCard.getAnswer());
    }

    @Test
    @Order(4)
    void testDeleteCard() {
        assertNotNull(cardService.getCardBy(SECOND_ID));
        assertDoesNotThrow(() -> cardService.delete(SECOND_ID));
        assertNull(cardService.getCardBy(SECOND_ID));
    }

    @Test
    @Order(5)
    void testJsonRpc() {
        final JsonRpcResponse success = processor.process(GET_REQUEST);
        assertTrue(success instanceof SuccessResponse);
        final JsonRpcResponse emptySuccess = processor.process(EMPTY_GET_REQUEST);
        assertTrue(emptySuccess instanceof SuccessResponse);
        final JsonRpcResponse error = processor.process(INCORRECT_GET_REQUEST);
        assertTrue(error instanceof ErrorResponse);
    }

    @AfterAll
    static void afterAll() throws Exception {
        cardService.close();
        if (mongoDbContainer != null) {
            if (mongoDbContainer.isRunning()) {
                mongoDbContainer.stop();
            }
        }
    }
}
