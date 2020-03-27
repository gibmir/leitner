package leitner.card.services.integration;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import leitner.card.Card;
import leitner.card.services.CardService;
import leitner.card.services.integration.containers.MongoDbContainer;
import leitner.card.services.mongo.MongoCardAdapter;
import leitner.card.services.mongo.MongoCardService;
import org.bson.Document;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static leitner.card.services.integration.containers.MongoDbContainer.DEFAULT_MONGO_PORT;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CardServiceTest {

    public static final String EXPECTED_QUESTION = "year";
    public static final String EXPECTED_ANSWER = "2020";
    private static CardService cardService;
    public static final long ID = 1L;
    private static MongoDbContainer mongoDbContainer;

    @BeforeAll
    static void beforeAll() {
        mongoDbContainer = new MongoDbContainer();
        mongoDbContainer.withExposedPorts(DEFAULT_MONGO_PORT);
        mongoDbContainer.start();
        final MongoClient mongoClient = new MongoClient("localhost", mongoDbContainer.getPort());
        final MongoDatabase leitner = mongoClient.getDatabase("leitner");
        final MongoCollection<Document> cards = leitner.getCollection("cards");
        final Card card = new Card(ID, EXPECTED_QUESTION, EXPECTED_ANSWER);
        cards.insertOne(MongoCardAdapter.toDocument(card));
        cardService = new MongoCardService(cards);
    }

    @Test
    @Order(1)
    void testGetCardById() {
        Optional<Card> optionalCard = cardService.getCardBy(ID);
        assertTrue(optionalCard.isPresent());
        Card card = optionalCard.get();
        assertEquals(ID, card.getId());
        assertEquals(EXPECTED_QUESTION, card.getQuestion());
        assertEquals(EXPECTED_ANSWER, card.getAnswer());
    }

    @Test
    @Order(2)
    void testSaveCard() {
        long id = 2L;
        final Card card = new Card(id, "sky color", "blue");
        assertDoesNotThrow(() -> cardService.save(card));
        assertTrue(cardService.getCardBy(id).isPresent());
    }

    @Test
    @Order(3)
    void testUpdateCard() {
        Optional<Card> optionalCard = cardService.getCardBy(ID);
        assertTrue(optionalCard.isPresent());
        Card card = optionalCard.get();
        String updatedAnswer = "2019";
        card.setAnswer(updatedAnswer);
        assertDoesNotThrow(() -> cardService.save(card));
        Optional<Card> updatedOptionalCard = cardService.getCardBy(ID);
        assertTrue(updatedOptionalCard.isPresent());
        Card updatedCard = updatedOptionalCard.get();
        assertEquals(updatedAnswer, updatedCard.getAnswer());
    }

    @Test
    @Order(4)
    void testDeleteCard() {
        assertTrue(cardService.getCardBy(ID).isPresent());
        assertDoesNotThrow(() -> cardService.delete(ID));
        assertFalse(cardService.getCardBy(ID).isPresent());
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
