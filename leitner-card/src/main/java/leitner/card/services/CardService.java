package leitner.card.services;

import leitner.card.Card;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Optional;

public class CardService implements AutoCloseable {
    private final EntityManager entityManager;

    public CardService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Card> getCardBy(Long id) {
        return Optional.ofNullable(entityManager.find(Card.class, id));
    }

    public void save(Card card) {
        entityManager.persist(card);
    }

    public void delete(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(entityManager.find(Card.class, id));
        transaction.commit();
    }

    @Override
    public void close() throws Exception {
        if (entityManager != null) {
            entityManager.clear();
            entityManager.close();
        }
    }
}
