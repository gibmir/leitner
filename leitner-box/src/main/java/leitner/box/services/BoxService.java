package leitner.box.services;

import leitner.box.model.Box;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Optional;

public class BoxService implements AutoCloseable  {
    private final EntityManager entityManager;

    public BoxService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Box> getBoxBy(Long id) {
        return Optional.ofNullable(entityManager.find(Box.class, id));
    }

    public void save(Box box) {
        entityManager.persist(box);
    }

    public void deleteBoxWith(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(entityManager.find(Box.class, id));
        transaction.commit();
    }

    @Override
    public void close() {
        if (entityManager != null) {
            entityManager.close();
        }
    }
}
