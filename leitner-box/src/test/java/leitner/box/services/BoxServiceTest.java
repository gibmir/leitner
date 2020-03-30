package leitner.box.services;

import leitner.box.model.Box;
import leitner.box.model.folder.Folder;
import leitner.box.model.folder.card.Card;
import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BoxServiceTest {
    private static final String PERSISTENCE_UNIT_NAME = "box-service-test";
    public static final String EXPECTED_BOX_TITLE = "testbox";
    public static final int EXPECTED_FOLDER_ID = 1;
    public static final int EXPECTED_CARD_ID = 1;
    public static final String ADDED_BOX_TITLE = "other-test-box";
    private static BoxService boxService;
    public static final long EXPECTED_BOX_ID = 1L;
    public static final long ADDED_ID = 2L;

    @BeforeAll
    static void beforeAll() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        boxService = new BoxService(entityManager);
    }

    @Test
    @Order(1)
    void testGetBoxById() {
        Optional<Box> optionalBox = boxService.getBoxBy(EXPECTED_BOX_ID);
        assertTrue(optionalBox.isPresent());
        Box box = optionalBox.get();
        assertEquals(EXPECTED_BOX_ID, box.getId());
        assertEquals(EXPECTED_BOX_TITLE, box.getTitle());
        Set<Folder> folders = box.getFolders();
        assertFalse(folders.isEmpty());
        assertEquals(1, folders.size());
        Folder folder = folders.iterator().next();
        assertEquals(EXPECTED_FOLDER_ID, folder.getId());
        Set<Card> cards = folder.getCards();
        assertFalse(cards.isEmpty());
        assertEquals(1, cards.size());
        Card card = cards.iterator().next();
        assertEquals(EXPECTED_CARD_ID, card.getId());
    }

    @Test
    @Order(2)
    void testSave() {
        Optional<Box> virtualBox = boxService.getBoxBy(ADDED_ID);
        assertFalse(virtualBox.isPresent());
        Box box = new Box();
        box.setId(ADDED_ID);
        box.setTitle(ADDED_BOX_TITLE);

        Folder folder = new Folder();
        folder.setId(ADDED_ID);
        folder.setBox(box);
        Card card = new Card();
        card.setId(ADDED_ID);
        card.setFolder(folder);
        folder.setCards(Collections.singleton(card));
        box.setFolders(Collections.singleton(folder));

        boxService.save(box);
        Optional<Box> addedBox = boxService.getBoxBy(ADDED_ID);
        assertTrue(addedBox.isPresent());
    }

    @Test
    @Order(3)
    void smoke() {
        Optional<Box> addedBox = boxService.getBoxBy(ADDED_ID);
        assertTrue(addedBox.isPresent());
        boxService.deleteBoxWith(ADDED_ID);
        Optional<Box> deletedBox = boxService.getBoxBy(ADDED_ID);
        assertFalse(deletedBox.isPresent());
    }

    @AfterAll
    static void afterAll() {
        boxService.close();
    }
}
