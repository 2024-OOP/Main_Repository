package MainRepository;

import java.util.Collections;
import java.util.LinkedList;

abstract public class Deck {
    private static LinkedList<Card> deck = new LinkedList<>();

    abstract void deckGenerate();
    void initialize() {
        deck.clear();
        deckGenerate();
        Collections.shuffle(deck);
    }

    static Card draw() {
        Card card = deck.getLast();
        deck.removeLast();
        return card;
    }

    // 외부에서 deck으로의 접근 제공
    protected static LinkedList<Card> getDeck() {
        return deck; 
    }
}
