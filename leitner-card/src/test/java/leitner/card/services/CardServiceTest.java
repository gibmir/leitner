package leitner.card.services;

import leitner.card.Card;
import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CardServiceTest {

    public static final String PERSISTENCE_UNIT_NAME = "card-service-test";
    public static final String EXPECTED_QUESTION = "year";
    public static final String EXPECTED_ANSWER = "2020";
    private static CardService cardService;
    public static final long ID = 1L;
    static StringBuilder builder = new StringBuilder();

    @BeforeAll
    static void beforeAll() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        cardService = new CardService(entityManager);
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
    }
}
