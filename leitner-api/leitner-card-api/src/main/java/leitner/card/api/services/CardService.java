package leitner.card.api.services;

import leitner.card.api.model.Card;

public interface CardService extends AutoCloseable {
    Card getCardBy(Long id);

    void save(Card card);

    void delete(Long id);
}
