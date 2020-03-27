package leitner.card.services.mongo;

import leitner.card.Card;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

public class MongoCardAdapter {

    public static final String ID_KEY = "id";
    public static final String QUESTION_KEY = "question";
    public static final String ANSWER_KEY = "answer";

    public static Document toDocument(Card card) {
        final Map<String, Object> keyValueMap = new HashMap<>();
        keyValueMap.put(ID_KEY, card.getId());
        keyValueMap.put(QUESTION_KEY, card.getQuestion());
        keyValueMap.put(ANSWER_KEY, card.getAnswer());
        return new Document(keyValueMap);
    }


    public static Card fromDocument(Document document) {
        final Long id = (Long) document.get(ID_KEY);
        final String question = (String) document.get(QUESTION_KEY);
        final String answer = (String) document.get(ANSWER_KEY);
        return new Card(id, question, answer);
    }

    public static Document searchById(Long id) {
        return new Document(ID_KEY, id);
    }
}
