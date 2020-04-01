package leitner.card.api.services;

import leitner.card.api.model.Card;

import java.util.Optional;

public interface CardService extends AutoCloseable {
    Optional<Card> getCardBy(Long id);

    void save(Card card);

    void delete(Long id);
}
