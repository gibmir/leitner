package leitner.card.services.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import leitner.card.api.model.Card;
import leitner.card.api.services.CardService;
import org.bson.Document;

public class MongoCardService implements CardService {
    private final MongoCollection<Document> cards;

    public MongoCardService(MongoCollection<Document> cards) {
        this.cards = cards;
    }

    @Override
    public Card getCardBy(Long id) {
        final Document searchQuery = MongoCardAdapter.searchById(id);
        final Document first = cards.find(searchQuery).first();
        if (first != null) {
            return MongoCardAdapter.fromDocument(first);
        } else {
            return null;
        }
    }

    @Override
    public void save(Card card) {
        final UpdateOptions updateOptions = new UpdateOptions();
        updateOptions.upsert(true);
        cards.updateOne(MongoCardAdapter.searchById(card.getId()),
                new Document("$set", MongoCardAdapter.toDocument(card)),
                updateOptions);
    }

    @Override
    public void delete(Long id) {
        cards.deleteOne(MongoCardAdapter.searchById(id));
    }

    @Override
    public void close() {
        //There is nothing to close
    }
}
